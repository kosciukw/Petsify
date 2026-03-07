package pl.kosciukw.petsify.feature.home.presentation

import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.view.ViewState

data class HomeState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
