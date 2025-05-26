package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.view.ViewState
import pl.kosciukw.petsify.shared.utils.empty

data class SignUpState(
    val inputName: String = String.empty(),
    val inputEmail: String = String.empty(),
    val inputPassword: String = String.empty(),
    val inputConfirmPassword: String = String.empty(),

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isTermsAccepted: Boolean = false,
    val isSignUpButtonStateEnabled: Boolean = false,
    val isNameValidationErrorEnabled: Boolean = false,
    val isEmailValidationErrorEnabled: Boolean = false,
    val isPasswordValidationErrorEnabled: Boolean = false,
    val isConfirmPasswordValidationErrorEnabled: Boolean = false,
    val isTermsErrorEnabled: Boolean = false
) : ViewState