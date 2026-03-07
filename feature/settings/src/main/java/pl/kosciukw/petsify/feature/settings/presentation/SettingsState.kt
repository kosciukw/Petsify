package pl.kosciukw.petsify.feature.settings.presentation

import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.view.ViewState

data class SettingsState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
