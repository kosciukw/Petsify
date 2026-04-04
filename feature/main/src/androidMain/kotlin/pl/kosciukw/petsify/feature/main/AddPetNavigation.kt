package pl.kosciukw.petsify.feature.main

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.main.presentation.ui.AddPetViewModel
import pl.kosciukw.petsify.shared.navigation.AppRoute
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

fun NavGraphBuilder.addPetScreen(
  onNavigateUp: () -> Unit
) {
  composable<AppRoute.AddPet> {
    val addPetViewModel: AddPetViewModel = koinInject()
    val stringsProvider: FeatureStringsProvider = koinInject()
    val state by addPetViewModel.state.collectAsState()

    DisposableEffect(addPetViewModel) {
      onDispose { addPetViewModel.clear() }
    }

    AddPetScreen(
      state = state,
      events = addPetViewModel::setEvent,
      action = addPetViewModel.action,
      strings = stringsProvider.addPet(),
      commonStrings = stringsProvider.common(),
      onNavigateUp = onNavigateUp
    )
  }
}
