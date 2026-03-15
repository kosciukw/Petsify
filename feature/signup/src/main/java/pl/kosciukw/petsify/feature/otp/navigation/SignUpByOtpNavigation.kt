package pl.kosciukw.petsify.feature.otp.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpScreen
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpViewModel
import pl.kosciukw.petsify.shared.navigation.typeMapEntry

@Serializable
data class SignUpByOtpDestination(
    val navArgs: SignUpByOtpNavArgs
)

fun NavGraphBuilder.signUpByOtpScreen(
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<SignUpByOtpDestination>(
        typeMap = mapOf(typeMapEntry<SignUpByOtpNavArgs>())
    ) { backStackEntry ->
        val signUpViewModel: SignUpByOtpViewModel = koinInject()
        val state by signUpViewModel.state.collectAsState()

        backStackEntry.toRoute<SignUpByOtpDestination>().let {
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
            events = { event -> signUpViewModel.setEvent(event) },
            onNavigateToMain = onNavigateToMain
        )
    }
}

fun NavController.navigateToSignUpByOtp(navArgs: SignUpByOtpNavArgs) {
    navigate(SignUpByOtpDestination(navArgs))
}
