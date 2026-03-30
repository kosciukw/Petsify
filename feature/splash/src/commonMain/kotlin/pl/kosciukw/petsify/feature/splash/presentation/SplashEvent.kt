package pl.kosciukw.petsify.feature.splash.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewEvent

sealed class SplashEvent : ViewEvent {
    data object OnStart : SplashEvent()
}
