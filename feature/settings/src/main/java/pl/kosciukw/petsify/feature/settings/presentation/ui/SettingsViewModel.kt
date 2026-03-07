package pl.kosciukw.petsify.feature.settings.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kosciukw.petsify.feature.settings.presentation.SettingsAction
import pl.kosciukw.petsify.feature.settings.presentation.SettingsEvent
import pl.kosciukw.petsify.feature.settings.presentation.SettingsState
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
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
