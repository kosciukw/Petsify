package pl.kosciukw.petsify.feature.signup.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.ui.components.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.utils.clear
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
//    private val signupUseCase: SignupUseCase,
//    private val emailIdentifierValidator: EmailIdentifierValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>,
//    private val passwordMatchValidator: PasswordMatchValidator,
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

    override fun onTriggerEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameTextChanged -> onNameTextChanged(event.value)
            else -> onNameTextChanged("event.value")
//            is SignUpEvent.OnEmailChanged -> onEmailChanged(event.value.toCharArray())
//            is SignUpEvent.OnPasswordChanged -> onPasswordChanged(event.value.toCharArray())
//            is SignUpEvent.OnRepeatPasswordChanged -> onRepeatPasswordChanged(event.value.toCharArray())
//            is SignUpEvent.OnTermsAcceptedChanged -> onTermsAcceptedChanged(event.accepted)
////            is SignUpEvent.Register -> register(
////                name = event.name,
////                email = event.email,
////                password = event.password,
////                repeatPassword = event.repeatPassword,
////                termsAccepted = event.termsAccepted
////            )
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

//    private fun onEmailChanged(email: CharArray) {
//        val identifier = EmailIdentifier(email)
//        val identifierState = emailIdentifierValidator.isValid(identifier)
//        isEmailValid = identifierState is IdentifierState.Valid
//        setState {
//            copy(
//                inputEmail = email.concatToString(),
//                isEmailValidationErrorEnabled = !isEmailValid
//            )
//        }
//        email.clear()
//        handleButtonState()
//    }

    private fun onPasswordChanged(password: CharArray) {
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

//    private fun onRepeatPasswordChanged(repeatPassword: CharArray) {
//        isRepeatPasswordValid = passwordMatchValidator.areEqual(
//            _state.value.inputPassword.toCharArray(),
//            repeatPassword
//        )
//        setState {
//            copy(
//                inputRepeatPassword = repeatPassword.concatToString(),
//                isRepeatPasswordValidationErrorEnabled = !isRepeatPasswordValid
//            )
//        }
//        repeatPassword.clear()
//        handleButtonState()
//    }

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

//    private fun register(
//        name: String,
//        email: String,
//        password: String,
//        repeatPassword: String,
//        termsAccepted: Boolean
//    ) {
//        viewModelScope.launch {
//            signupUseCase.action(
//                SignupUseCase.Params(
//                    name = name,
//                    email = email,
//                    password = password
//                )
//            ).collect { result ->
//                when (result) {
//                    is ResultOrFailure.Loading -> {
//                        setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
//                    }
//                    is ResultOrFailure.Success -> {
//                        setState { copy(progressBarState = ProgressBarState.Idle) }
//                        setAction { SignUpAction.Navigation.NavigateToMain }
//                    }
//                    is ResultOrFailure.Failure -> {
//                        onFailure(result.error)
//                        setState { copy(progressBarState = ProgressBarState.Idle) }
//                    }
//                }
//            }
//        }
//    }

    private fun handleButtonState() {
        val enabled = isNameValid && isEmailValid && isPasswordValid && isRepeatPasswordValid && isTermsAccepted
        setState { copy(isSignUpButtonStateEnabled = enabled) }
    }
}