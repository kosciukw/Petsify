package pl.kosciukw.petsify.feature.splash.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewState

data class SplashState(
    val isLoading: Boolean = true
) : ViewState
