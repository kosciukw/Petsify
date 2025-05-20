package pl.kosciukw.petsify.shared.ui.components.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.ui.components.UIComponent
import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent
import pl.kosciukw.petsify.shared.ui.components.view.ViewSingleAction
import pl.kosciukw.petsify.shared.ui.components.view.ViewState

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, SingleAction : ViewSingleAction>(
    private val integrationErrorMapper: IntegrationErrorMapper
) : ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun onTriggerEvent(event: Event)

    private val initialState: UiState by lazy { setInitialState() }
    protected val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state = _state

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _action: Channel<SingleAction> = Channel()
    val action = _action.receiveAsFlow()

    private val _errors: MutableSharedFlow<UIComponent> = MutableSharedFlow()
    val errors = _errors.asSharedFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                onTriggerEvent(it)
            }
        }
    }

    // Set event to trigger the logic in the ViewModel
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    // Update the UI state using a reducer function
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    // Send single action to be processed by the UI
    protected fun setAction(builder: () -> SingleAction) {
        val effectValue = builder()
        viewModelScope.launch { _action.send(effectValue) }
    }

    private fun setError(uiComponent: () -> UIComponent) {
        viewModelScope.launch(Dispatchers.Main) {
            _errors.emit(uiComponent())
        }
    }

    open fun onFailure(
        retry: () -> Unit = {},
        onErrorAcknowledged: () -> Unit = {},
        error: Throwable,
        showMessage: Boolean = true
    ) {

        when (error) {
            is DomainError -> processDomainError(
                retry = retry,
                onErrorAcknowledged = onErrorAcknowledged,
                error = error,
                showMessage = showMessage
            )

            else -> {
               //no-op
            }
        }
    }

    private fun processDomainError(
        retry: () -> Unit = {},
        onErrorAcknowledged: () -> Unit = {},
        error: DomainError,
        showMessage: Boolean = true
    ) {
        integrationErrorMapper.map(error = error).let { appError ->
            when (appError) {
                is AppError.InfoError -> setError {
                    UIComponent.ToastSimple(title = appError.uiMessage)
                }

                is AppError.TechnicalError.SessionExpired -> {
                    //no-op
                }

                else -> {
                    //no-op
                }
            }
        }
    }
}