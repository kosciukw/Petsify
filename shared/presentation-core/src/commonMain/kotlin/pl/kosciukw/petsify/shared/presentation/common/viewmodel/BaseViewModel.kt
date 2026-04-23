package pl.kosciukw.petsify.shared.presentation.common.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.presentation.components.view.ViewEvent
import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, SingleAction : ViewSingleAction>(
    private val integrationErrorMapper: IntegrationErrorMapper,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
) {

    abstract fun setInitialState(): UiState
    abstract fun onTriggerEvent(event: Event)

    private val initialState: UiState by lazy { setInitialState() }
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()

    private val _action = Channel<SingleAction>(capacity = Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    private val _errors = MutableSharedFlow<UIComponent>()
    val errors = _errors.asSharedFlow()

    init {
        coroutineScope.launch {
            _event.collect { onTriggerEvent(it) }
        }
    }

    fun setEvent(event: Event) {
        coroutineScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        _state.value = _state.value.reducer()
    }

    protected fun setAction(builder: () -> SingleAction) {
        coroutineScope.launch { _action.send(builder()) }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }

    fun clear() {
        coroutineScope.cancel()
    }

    private fun setError(uiComponent: () -> UIComponent) {
        coroutineScope.launch(Dispatchers.Main.immediate) {
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
                // no-op
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
                    // no-op
                }

                else -> {
                    // no-op
                }
            }
        }
    }
}
