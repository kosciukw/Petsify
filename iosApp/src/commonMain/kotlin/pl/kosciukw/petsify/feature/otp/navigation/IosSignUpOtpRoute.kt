package pl.kosciukw.petsify.feature.otp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpScreen
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpViewModel
import pl.kosciukw.petsify.iosapp.di.rememberKoinInstance
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

@Composable
fun IosSignUpOtpRoute(
    navArgs: SignUpByOtpNavArgs,
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val signUpByOtpViewModel = rememberKoinInstance<SignUpByOtpViewModel>()
    val stringsProvider = rememberKoinInstance<FeatureStringsProvider>()
    val state by signUpByOtpViewModel.state.collectAsState()

    DisposableEffect(signUpByOtpViewModel) {
        signUpByOtpViewModel.onNavArgsProvided(navArgs)
        onDispose { signUpByOtpViewModel.clear() }
    }

    SignUpByOtpScreen(
        state = state,
        onNavigateToMain = onNavigateToMain,
        onNavigateUp = onNavigateUp,
        errors = signUpByOtpViewModel.errors,
        events = signUpByOtpViewModel::setEvent,
        action = signUpByOtpViewModel.action,
        strings = stringsProvider.otp(),
        commonStrings = stringsProvider.common()
    )
}
