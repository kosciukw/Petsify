package pl.kosciukw.petsify.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashScreen
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashViewModel

@Serializable
object SplashDestination

fun NavGraphBuilder.splashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    composable<SplashDestination> {

        val signUpViewModel: SplashViewModel = koinViewModel()
        val state = signUpViewModel.state.value
        val action = signUpViewModel.action
        val errors = signUpViewModel.errors

        SplashScreen(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToMain = onNavigateToMain,
            state = state,
            action = action,
            errors = errors,
            events = { event -> signUpViewModel.setEvent(event) }
        )
    }
}
