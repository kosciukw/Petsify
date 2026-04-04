package pl.kosciukw.petsify.feature.otp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpScreen
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpViewModel
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

@Composable
fun IosSignUpOtpRoute(
    navArgs: SignUpByOtpNavArgs,
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val signUpByOtpViewModel = remember { KoinPlatform.getKoin().get<SignUpByOtpViewModel>() }
    val stringsProvider = remember { KoinPlatform.getKoin().get<FeatureStringsProvider>() }
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
