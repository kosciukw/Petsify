package pl.kosciukw.petsify.feature.signup.presentation.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.feature.signup.usecase.StartOtpRegistrationUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchState
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordValidator
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val startOtpRegistrationUseCase: StartOtpRegistrationUseCase,
    private val emailIdentifierValidator: EmailIdentifierValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>,
    private val passwordValidator: PasswordValidator,
    private val passwordMatchValidator: PasswordMatchValidator,
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<SignUpEvent, SignUpState, SignUpAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = SignUpState()

    private var isNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isRepeatPasswordValid = false
    private var isTermsAccepted = false
    private var isMarketingAccepted = false

    override fun onTriggerEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameTextChanged -> onNameTextChanged(event.value)
            is SignUpEvent.OnEmailTextChanged -> onEmailChanged(event.value.toCharArray())
            is SignUpEvent.OnPasswordChanged -> onPasswordChanged(event.value.toCharArray())
            is SignUpEvent.OnConfirmPasswordChanged -> onConfirmPasswordChanged(event.value.toCharArray())
            is SignUpEvent.OnTermsAcceptedChanged -> onTermsAcceptedChanged(event.accepted)
            is SignUpEvent.OnMarketingAcceptedChanged -> onMarketingAccepted(event.accepted)
            is SignUpEvent.OnConfirmButtonClicked -> onConfirmButtonClicked()
            is SignUpEvent.OnLoginButtonClicked -> { setAction { SignUpAction.Navigation.NavigateToLogin } }
        }
    }

    private fun onNameTextChanged(name: String) {
        isNameValid = name.isNotBlank()
        setState {
            copy(
                inputName = name,
                isNameValidationErrorEnabled = !isNameValid
            )
        }
        handleButtonState()
    }

    private fun onEmailChanged(email: CharArray) {
        val identifier = EmailIdentifier(email)
        val identifierState = emailIdentifierValidator.isValid(identifier)
        isEmailValid = identifierState is IdentifierState.Valid
        setState {
            copy(
                inputEmail = email.concatToString(),
                isEmailValidationErrorEnabled = !isEmailValid
            )
        }
        handleButtonState()
    }

    private fun onPasswordChanged(password: CharArray) {
        isPasswordValid = passwordValidator.isValid(password)

        val matchState = passwordMatchValidator.isValid(
            password = password,
            confirmPassword = _state.value.inputConfirmPassword.toCharArray()
        )

        setState {
            copy(
                inputPassword = password.concatToString(),
                isPasswordValidationErrorEnabled = !isPasswordValid
            )
        }

        when (matchState) {
            is PasswordMatchState.Match -> {
                setState {
                    copy(isConfirmPasswordValidationErrorEnabled = false)
                }
            }

            is PasswordMatchState.Mismatch -> {
                setState {
                    copy(isConfirmPasswordValidationErrorEnabled = true)
                }

            }

            is PasswordMatchState.Empty -> {
                //no-op
            }
        }

        handleButtonState()
    }

    private fun onConfirmPasswordChanged(confirmPassword: CharArray) {
        val matchState = passwordMatchValidator.isValid(
            password = _state.value.inputPassword.toCharArray(),
            confirmPassword = confirmPassword
        )

        setState {
            copy(
                inputConfirmPassword = confirmPassword.concatToString(),
            )
        }

        when (matchState) {
            is PasswordMatchState.Match -> {
                setState {
                    copy(isConfirmPasswordValidationErrorEnabled = false)
                }
            }

            is PasswordMatchState.Mismatch -> {
                setState {
                    copy(isConfirmPasswordValidationErrorEnabled = true)
                }
            }

            is PasswordMatchState.Empty -> {
                setState {
                    copy(inputConfirmPassword = confirmPassword.concatToString())
                }
            }
        }

        handleButtonState()
    }

    private fun onTermsAcceptedChanged(accepted: Boolean) {
        isTermsAccepted = accepted
        setState {
            copy(
                isTermsAccepted = accepted,
                isTermsErrorEnabled = !accepted
            )
        }
        handleButtonState()
    }

    private fun onMarketingAccepted(accepted: Boolean) {
        isMarketingAccepted = accepted
        setState {
            copy(
                isMarketingAccepted = accepted,
                isMarketingErrorEnabled = !accepted
            )
        }
        handleButtonState()
    }

    private fun onConfirmButtonClicked() {
        startOtpRegistration(_state.value.inputEmail)
    }

    private fun startOtpRegistration(
        email: String
    ) {
        viewModelScope.launch {
            startOtpRegistrationUseCase.action(
                StartOtpRegistrationUseCase.Params(email = email)
            ).collect { result ->
                when (result) {
                    is ResultOrFailure.Loading -> {
                        setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
                    }

                    is ResultOrFailure.Success -> {
                        setState { copy(progressBarState = ProgressBarState.Idle) }

                        // TODO 26.06.2025 handle finalize registration process
                    }

                    is ResultOrFailure.Failure -> {
                        onFailure(error = result.error)
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                    }
                }
            }
        }
    }

    private fun handleButtonState() {
        val enabled = isNameValid
                && isEmailValid
                && isPasswordValid
                && isRepeatPasswordValid
                && isTermsAccepted
                && isMarketingAccepted
        setState { copy(isSignUpButtonStateEnabled = enabled) }
    }
}