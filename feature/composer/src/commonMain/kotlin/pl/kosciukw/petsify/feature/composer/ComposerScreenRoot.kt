package pl.kosciukw.petsify.feature.composer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.kosciukw.petsify.feature.composersettings.ComposerSettingsScreen

@Composable
fun ComposerScreenRoot(
  onNavigateUp: () -> Unit
) {
  var screen by remember { mutableStateOf(ComposerRootScreen.Compose) }
  when (screen) {
    ComposerRootScreen.Compose -> {
      ComposerScreen(
        onNavigateUp = onNavigateUp,
        onOpenSettings = { screen = ComposerRootScreen.Settings }
      )
    }

    ComposerRootScreen.Settings -> {
      ComposerSettingsScreen(
        onNavigateUp = { screen = ComposerRootScreen.Compose }
      )
    }
  }
}

private enum class ComposerRootScreen {
  Compose,
  Settings
}
