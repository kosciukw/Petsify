package pl.kosciukw.petsify.feature.settings.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsScreen
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsViewModel

@Serializable
private data object SettingsDestination

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsDestination> {
        val settingsViewModel: SettingsViewModel = koinInject()
        val state by settingsViewModel.state.collectAsState()

        DisposableEffect(settingsViewModel) {
            onDispose { settingsViewModel.clear() }
        }

        SettingsScreen(
            state = state,
            action = settingsViewModel.action,
            errors = settingsViewModel.errors,
            events = { event -> settingsViewModel.setEvent(event) }
        )
    }
}

fun NavController.navigateToSettings() {
    navigate(SettingsDestination) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
