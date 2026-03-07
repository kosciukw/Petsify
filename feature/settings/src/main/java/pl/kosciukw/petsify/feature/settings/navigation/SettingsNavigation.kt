package pl.kosciukw.petsify.feature.settings.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsScreen
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsViewModel

@Serializable
private data object SettingsDestination

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsDestination> {
        val settingsViewModel: SettingsViewModel = hiltViewModel()
        val state = settingsViewModel.state.value
        val action = settingsViewModel.action
        val errors = settingsViewModel.errors

        SettingsScreen(
            state = state,
            action = action,
            errors = errors,
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
