package pl.kosciukw.petsify.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.kosciukw.petsify.shared.navigation.AppRoute

fun NavGraphBuilder.mainScreen(
  onOpenEmailDetails: (emailId: Int) -> Unit,
  onComposeNewEmail: () -> Unit
) {
  composable<AppRoute.Main> {
    MainScreen(
      onOpenEmailDetails = onOpenEmailDetails,
      onComposeNewEmail = onComposeNewEmail
    )
  }
}

fun NavController.navigateToMain() {
  navigate(AppRoute.Main) {
    popUpTo(0) {
      inclusive = true
    }
  }
}
