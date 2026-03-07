package pl.kosciukw.petsify.feature.settings.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class SettingsEvent : ViewEvent {
    data object OnStart : SettingsEvent()
}
