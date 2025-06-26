package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewSingleAction

sealed class SignUpAction : ViewSingleAction {

    sealed class Navigation : SignUpAction() {

        data object NavigateToOtp : Navigation()

        data object NavigateToLogin : Navigation()
    }
}