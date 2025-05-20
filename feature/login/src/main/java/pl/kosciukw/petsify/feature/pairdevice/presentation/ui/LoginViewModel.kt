package pl.kosciukw.petsify.feature.pairdevice.presentation.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidator
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginAction
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginEvent
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginState
import pl.kosciukw.petsify.feature.pairdevice.usecase.PairDeviceUseCase
import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.utils.clear
import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val pairDeviceUseCase: PairDeviceUseCase,
    private val emailIdentifierValidator: EmailIdentifierValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>,
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<LoginEvent, LoginState, LoginAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = LoginState()
    private var identifierState: IdentifierState = IdentifierState.Invalid
    private var isPasswordValid: Boolean = false
    private var isEmailValid: Boolean = false

    override fun onTriggerEvent(event: LoginEvent) {

        when (event) {
            is LoginEvent.Login -> {
                val email = event.email
                val password = event.password
                login(email, password)
            }

            is LoginEvent.OnEmailTextChanged -> {
                onEmailTextChanged(event.value.toCharArray())
            }

            is LoginEvent.OnPasswordTextChanged -> {
                onPasswordTextChanged(event.value.toCharArray())
            }

            else -> {
                //no-op
            }
        }
    }

    private fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            pairDeviceUseCase.action(
                PairDeviceUseCase.Params(
                    email,
                    password
                )
            ).collect { result ->
                when (result) {
                    is ResultOrFailure.Loading -> {
                        _state.value = _state.value.copy(
                            progressBarState = ProgressBarState.ScreenLoading
                        )
                    }

                    is ResultOrFailure.Success -> {
                        _state.value = _state.value.copy(
                            progressBarState = ProgressBarState.Idle
                        )

                        setAction {
                            LoginAction.Navigation.NavigateToMain
                        }
                    }

                    is ResultOrFailure.Failure -> {
                        onFailure(error = result.error)

                        _state.value = _state.value.copy(
                            progressBarState = ProgressBarState.Idle
                        )
                    }
                }
            }
        }
    }


    private fun onPasswordTextChanged(password: CharArray) {
        isPasswordValid = notEmptyValidator.isValid(password)
        setState {
            copy(
                inputPassword = password.concatToString(),
                isPasswordValidationErrorEnabled = !isPasswordValid
            )
        }

        password.clear()
        handleButtonState()
    }

    private fun onEmailTextChanged(email: CharArray) {
        EmailIdentifier(email = email).also { identifier ->
            identifierState = emailIdentifierValidator.isValid(identifier = identifier)
        }

        isEmailValid = identifierState is IdentifierState.Valid
        setState {
            copy(
                inputEmail = email.concatToString(),
                isEmailValidationErrorEnabled = !isEmailValid
            )
        }
        handleButtonState()
    }

    private fun handleButtonState() {
        setState { copy(isLoginButtonEnabled = isPasswordValid && isEmailValid) }
    }

    private fun onRetryNetwork() {}

    private fun onUpdateNetworkState(networkState: NetworkState) {
        setState { copy(networkState = networkState) }
    }
}