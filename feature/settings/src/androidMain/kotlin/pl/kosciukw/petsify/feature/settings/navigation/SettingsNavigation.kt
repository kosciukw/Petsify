package pl.kosciukw.petsify.feature.settings.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsScreen
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsViewModel
import pl.kosciukw.petsify.shared.navigation.AppRoute
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

fun NavGraphBuilder.settingsScreen() {
    composable<AppRoute.Settings> {
        val settingsViewModel: SettingsViewModel = koinInject()
        val stringsProvider: FeatureStringsProvider = koinInject()
        val state by settingsViewModel.state.collectAsState()

        DisposableEffect(settingsViewModel) {
            onDispose { settingsViewModel.clear() }
        }

        SettingsScreen(
            state = state,
            action = settingsViewModel.action,
            errors = settingsViewModel.errors,
            events = settingsViewModel::setEvent,
            strings = stringsProvider.settings(),
            commonStrings = stringsProvider.common()
        )
    }
}

fun NavController.navigateToSettings() {
    navigate(AppRoute.Settings) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
