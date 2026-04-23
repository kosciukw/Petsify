package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction

sealed class SignUpAction : ViewSingleAction {

    sealed class Navigation : SignUpAction() {
        data class NavigateToOtp(val navArgs: SignUpByOtpNavArgs) : Navigation()
        data object NavigateToLogin : Navigation()
    }
}
