package pl.kosciukw.petsify.feature.signup.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpScreen
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpViewModel
import pl.kosciukw.petsify.shared.navigation.AppRoute
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

fun NavGraphBuilder.signUpScreen(
    onNavigateToOtp: (SignUpByOtpNavArgs) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<AppRoute.SignUp> {
        val signUpViewModel: SignUpViewModel = koinInject()
        val stringsProvider: FeatureStringsProvider = koinInject()
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
            events = signUpViewModel::setEvent,
            strings = stringsProvider.signUp(),
            commonStrings = stringsProvider.common()
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(AppRoute.SignUp)
}
