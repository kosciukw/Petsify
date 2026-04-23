package pl.kosciukw.petsify.feature.composer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.kosciukw.petsify.shared.navigation.AppRoute

fun NavGraphBuilder.composerScreenRoot(
  onNavigateUp: () -> Unit
) {
  composable<AppRoute.Composer> {
    ComposerScreenRoot(
      onNavigateUp = onNavigateUp
    )
  }
}

fun NavController.navigateToComposer() {
  navigate(AppRoute.Composer) {
    popUpTo<AppRoute.Composer> {
      inclusive = true
    }
  }
}
