package pl.kosciukw.petsify.feature.splash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashScreen
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashViewModel
import pl.kosciukw.petsify.iosapp.di.rememberKoinInstance

@Composable
fun IosSplashRoute(
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val splashViewModel = rememberKoinInstance<SplashViewModel>()
    val state by splashViewModel.state.collectAsState()

    DisposableEffect(splashViewModel) {
        onDispose { splashViewModel.clear() }
    }

    SplashScreen(
        state = state,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToMain = onNavigateToMain,
        errors = splashViewModel.errors,
        events = splashViewModel::setEvent,
        action = splashViewModel.action
    )
}
