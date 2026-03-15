package pl.kosciukw.petsify.feature.login.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.login.presentation.ui.LoginScreen
import pl.kosciukw.petsify.feature.login.presentation.ui.LoginViewModel

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable<LoginDestination> {
        val loginViewModel: LoginViewModel = koinInject()
        val state by loginViewModel.state.collectAsState()

        DisposableEffect(loginViewModel) {
            onDispose { loginViewModel.clear() }
        }

        LoginScreen(
            errors = loginViewModel.errors,
            state = state,
            onNavigateToMain = onNavigateToMain,
            onNavigateToSignUp = onNavigateToSignUp,
            events = { event -> loginViewModel.setEvent(event) },
            action = loginViewModel.action,
            context = LocalContext.current
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(LoginDestination)
}
