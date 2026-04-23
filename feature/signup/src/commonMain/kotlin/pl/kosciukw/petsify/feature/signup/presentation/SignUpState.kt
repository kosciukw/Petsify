package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState

data class SignUpState(
    val inputName: String = "",
    val inputEmail: String = "",
    val inputPassword: String = "",
    val inputConfirmPassword: String = "",
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isTermsAccepted: Boolean = false,
    val isMarketingAccepted: Boolean = false,
    val isSignUpButtonStateEnabled: Boolean = false,
    val isNameValidationErrorEnabled: Boolean = false,
    val isEmailValidationErrorEnabled: Boolean = false,
    val isPasswordValidationErrorEnabled: Boolean = false,
    val isConfirmPasswordValidationErrorEnabled: Boolean = false,
    val isTermsErrorEnabled: Boolean = false,
    val isMarketingErrorEnabled: Boolean = false
) : ViewState
