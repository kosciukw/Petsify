package pl.kosciukw.petsify.feature.pairdevice.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.feature.pairdevice.presentation.ui.LoginScreen
import pl.kosciukw.petsify.feature.pairdevice.presentation.ui.LoginViewModel

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable<LoginDestination> {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val state = loginViewModel.state.value
        val action = loginViewModel.action

        LoginScreen(
            errors = loginViewModel.errors,
            state = state,
            onNavigateToMain = onNavigateToMain,
            onNavigateToSignUp = onNavigateToSignUp,
            events = { event -> loginViewModel.setEvent(event) },
            action = action,
            context = LocalContext.current
        )
    }
}