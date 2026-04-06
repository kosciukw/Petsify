package pl.kosciukw.petsify.feature.otp.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpScreen
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpViewModel
import pl.kosciukw.petsify.shared.navigation.AppRoute
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.navigation.typeMapEntry
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

fun NavGraphBuilder.signUpByOtpScreen(
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<AppRoute.SignUpOtp>(
        typeMap = mapOf(typeMapEntry<SignUpByOtpNavArgs>())
    ) { backStackEntry ->
        val signUpViewModel: SignUpByOtpViewModel = koinInject()
        val stringsProvider: FeatureStringsProvider = koinInject()
        val state by signUpViewModel.state.collectAsState()

        backStackEntry.toRoute<AppRoute.SignUpOtp>().let {
            signUpViewModel.onNavArgsProvided(it.navArgs)
        }

        DisposableEffect(signUpViewModel) {
            onDispose { signUpViewModel.clear() }
        }

        SignUpByOtpScreen(
            onNavigateUp = onNavigateUp,
            state = state,
            action = signUpViewModel.action,
            errors = signUpViewModel.errors,
            events = signUpViewModel::setEvent,
            onNavigateToMain = onNavigateToMain,
            strings = stringsProvider.otp(),
            commonStrings = stringsProvider.common()
        )
    }
}

fun NavController.navigateToSignUpByOtp(navArgs: SignUpByOtpNavArgs) {
    navigate(AppRoute.SignUpOtp(navArgs))
}
