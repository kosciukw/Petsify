package pl.kosciukw.petsify.feature.addpet.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform
import pl.kosciukw.petsify.feature.addpet.presentation.ui.AddPetViewModel
import pl.kosciukw.petsify.feature.addpet.ui.AddPetScreen
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

@Composable
fun IosAddPetRoute(
    onNavigateUp: () -> Unit
) {
    val addPetViewModel = remember { KoinPlatform.getKoin().get<AddPetViewModel>() }
    val stringsProvider = remember { KoinPlatform.getKoin().get<FeatureStringsProvider>() }
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
