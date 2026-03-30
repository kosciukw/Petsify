package pl.kosciukw.petsify.feature.splash.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashScreen
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashViewModel
import pl.kosciukw.petsify.shared.navigation.AppRoute

fun NavGraphBuilder.splashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    composable<AppRoute.Splash> {
        val splashViewModel: SplashViewModel = koinInject()
        val state by splashViewModel.state.collectAsState()

        DisposableEffect(splashViewModel) {
            onDispose { splashViewModel.clear() }
        }

        SplashScreen(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToMain = onNavigateToMain,
            state = state,
            action = splashViewModel.action,
            errors = splashViewModel.errors,
            events = { event -> splashViewModel.setEvent(event) }
        )
    }
}
