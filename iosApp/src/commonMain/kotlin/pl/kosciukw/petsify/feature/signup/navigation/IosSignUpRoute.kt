package pl.kosciukw.petsify.feature.signup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpScreen
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpViewModel
import pl.kosciukw.petsify.iosapp.di.rememberKoinInstance
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

@Composable
fun IosSignUpRoute(
    onNavigateToOtp: (SignUpByOtpNavArgs) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val signUpViewModel = rememberKoinInstance<SignUpViewModel>()
    val stringsProvider = rememberKoinInstance<FeatureStringsProvider>()
    val state by signUpViewModel.state.collectAsState()

    DisposableEffect(signUpViewModel) {
        onDispose { signUpViewModel.clear() }
    }

    SignUpScreen(
        state = state,
        onNavigateToOtp = onNavigateToOtp,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateUp = onNavigateUp,
        errors = signUpViewModel.errors,
        events = signUpViewModel::setEvent,
        action = signUpViewModel.action,
        strings = stringsProvider.signUp(),
        commonStrings = stringsProvider.common()
    )
}
