package pl.kosciukw.petsify.feature.pairdevice.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewSingleAction

sealed class LoginAction : ViewSingleAction {

    sealed class Navigation : LoginAction() {

        data object NavigateToMain : Navigation()

        data object NavigateToLogin : Navigation()
    }
}