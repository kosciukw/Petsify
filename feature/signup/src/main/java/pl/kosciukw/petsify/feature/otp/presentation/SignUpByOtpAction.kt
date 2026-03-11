package pl.kosciukw.petsify.feature.otp.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewSingleAction

sealed class SignUpByOtpAction : ViewSingleAction {

    sealed class Navigation : SignUpByOtpAction() {

        data object NavigateToMain : Navigation()
    }
}
