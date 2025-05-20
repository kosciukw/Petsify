package pl.kosciukw.petsify.shared.ui.components.progress

sealed class ProgressBarState{

   data object ButtonLoading: ProgressBarState()

   data object ScreenLoading: ProgressBarState()

   data object FullScreenLoading: ProgressBarState()

   data object LoadingWithLogo: ProgressBarState()

   data object Idle: ProgressBarState()

}