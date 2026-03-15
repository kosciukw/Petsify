package pl.kosciukw.petsify.feature.signup.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.otp.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpScreen
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpViewModel

@Serializable
private data object SignUpDestination

fun NavGraphBuilder.signUpScreen(
    onNavigateToOtp: (SignUpByOtpNavArgs) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<SignUpDestination> {
        val signUpViewModel: SignUpViewModel = koinInject()
        val state by signUpViewModel.state.collectAsState()

        DisposableEffect(signUpViewModel) {
            onDispose { signUpViewModel.clear() }
        }

        SignUpScreen(
            onNavigateToOtp = onNavigateToOtp,
            onNavigateUp = onNavigateUp,
            onNavigateToLogin = onNavigateToLogin,
            state = state,
            action = signUpViewModel.action,
            errors = signUpViewModel.errors,
            events = { event -> signUpViewModel.setEvent(event) }
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SignUpDestination)
}
