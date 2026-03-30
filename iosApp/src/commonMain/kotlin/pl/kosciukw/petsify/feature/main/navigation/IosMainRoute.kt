package pl.kosciukw.petsify.feature.main.navigation

import androidx.compose.runtime.Composable
import pl.kosciukw.petsify.feature.main.MainScreen

@Composable
fun IosMainRoute(
    onOpenEmailDetails: (Int) -> Unit,
    onComposeNewEmail: () -> Unit
) {
    MainScreen(
        onOpenEmailDetails = onOpenEmailDetails,
        onComposeNewEmail = onComposeNewEmail
    )
}
