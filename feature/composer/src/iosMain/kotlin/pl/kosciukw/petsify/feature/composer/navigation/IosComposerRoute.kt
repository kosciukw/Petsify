package pl.kosciukw.petsify.feature.composer.navigation

import androidx.compose.runtime.Composable
import pl.kosciukw.petsify.feature.composer.ComposerScreenRoot

@Composable
fun IosComposerRoute(
    onNavigateUp: () -> Unit
) {
    ComposerScreenRoot(onNavigateUp = onNavigateUp)
}
