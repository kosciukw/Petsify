package pl.kosciukw.petsify.feature.signup.presentation.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.feature.signup.usecase.SignUpUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.utils.clear
import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordValidator
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val emailIdentifierValidator: EmailIdentifierValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>,
    private val passwordValidator: PasswordValidator,
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
    private var isMarketingAccepted = false

    override fun onTriggerEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnNameTextChanged -> onNameTextChanged(event.value)
            is SignUpEvent.OnEmailTextChanged -> onEmailChanged(event.value.toCharArray())
            is SignUpEvent.OnPasswordChanged -> onPasswordChanged(event.value.toCharArray())
            is SignUpEvent.OnConfirmPasswordChanged -> onConfirmPasswordChanged(event.value.toCharArray())
            is SignUpEvent.OnTermsAcceptedChanged -> onTermsAcceptedChanged(event.accepted)
            is SignUpEvent.OnMarketingAcceptedChanged -> onMarketingAccepted(event.accepted)
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
        email.clear()
        handleButtonState()
    }

    private fun onPasswordChanged(password: CharArray) {
        isPasswordValid = passwordValidator.isValid(password)
        setState {
            copy(
                inputPassword = password.concatToString(),
                isPasswordValidationErrorEnabled = !isPasswordValid
            )
        }
//        password.clear()
        handleButtonState()
    }

    private fun onConfirmPasswordChanged(confirmPassword: CharArray) {
        isRepeatPasswordValid =
            confirmPassword.contentEquals(_state.value.inputPassword.toCharArray())
//        isRepeatPasswordValid = passwordMatchValidator.areEqual(
//            _state.value.inputPassword.toCharArray(),
//            confirmPassword
//        )
        setState {
            copy(
                inputConfirmPassword = confirmPassword.concatToString(),
                isConfirmPasswordValidationErrorEnabled = !isRepeatPasswordValid
            )
        }
//        confirmPassword.clear()
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


    private fun signUp(
        name: String,
        email: String,
        password: String,
        termsAccepted: Boolean
    ) {
        viewModelScope.launch {
            signUpUseCase.action(
                SignUpUseCase.Params(
                    name = name,
                    email = email,
                    password = password,
                    termsAccepted = termsAccepted,
                    marketingAccepted = isMarketingAccepted
                )
            ).collect { result ->
                when (result) {
                    is ResultOrFailure.Loading -> {
                        setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
                    }

                    is ResultOrFailure.Success -> {
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                        setAction { SignUpAction.Navigation.NavigateToMain }
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
        setState { copy(isSignUpButtonStateEnabled = enabled) }
    }
}