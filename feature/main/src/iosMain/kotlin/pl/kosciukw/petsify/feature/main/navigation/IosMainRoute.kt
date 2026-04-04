package pl.kosciukw.petsify.feature.main.navigation

import androidx.compose.runtime.Composable
import pl.kosciukw.petsify.feature.main.MainScreen

@Composable
fun IosMainRoute(
    onNavigateToAddPet: () -> Unit
) {
    MainScreen(
        onNavigateToAddPet = onNavigateToAddPet
    )
}
