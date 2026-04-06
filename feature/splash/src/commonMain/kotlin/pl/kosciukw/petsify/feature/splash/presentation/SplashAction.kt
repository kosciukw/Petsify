package pl.kosciukw.petsify.feature.splash.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction

sealed class SplashAction : ViewSingleAction {
    sealed class Navigation : SplashAction() {
        data object NavigateToLogin : Navigation()
        data object NavigateToMain : Navigation()
    }
}
