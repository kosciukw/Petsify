package pl.kosciukw.petsify.feature.home.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class HomeEvent : ViewEvent {
    data object OnStart : HomeEvent()
}
