package pl.kosciukw.petsify.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.kosciukw.petsify.shared.navigation.AppRoute

fun NavGraphBuilder.mainScreen(
  onNavigateToAddPet: () -> Unit
) {
  composable<AppRoute.Main> {
    MainScreen(
      onNavigateToAddPet = onNavigateToAddPet
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

fun NavController.navigateToAddPet() {
  navigate(AppRoute.AddPet)
}
