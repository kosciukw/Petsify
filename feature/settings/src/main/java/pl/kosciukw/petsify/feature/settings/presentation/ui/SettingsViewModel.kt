package pl.kosciukw.petsify.feature.settings.presentation.ui

import pl.kosciukw.petsify.feature.settings.presentation.SettingsAction
import pl.kosciukw.petsify.feature.settings.presentation.SettingsEvent
import pl.kosciukw.petsify.feature.settings.presentation.SettingsState
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.presentation.viewmodel.BaseViewModel

class SettingsViewModel(
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<SettingsEvent, SettingsState, SettingsAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = SettingsState()

    override fun onTriggerEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnStart -> {
                // no-op
            }
        }
    }
}
