package pl.kosciukw.petsify.feature.otp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
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

        val signUpViewModel: SignUpByOtpViewModel = hiltViewModel()
        val state = signUpViewModel.state.value
        val action = signUpViewModel.action
        val errors = signUpViewModel.errors

        backStackEntry.toRoute<SignUpByOtpDestination>().let {
            signUpViewModel.onNavArgsProvided(it.navArgs)
        }

        SignUpByOtpScreen(
            onNavigateUp = onNavigateUp,
            state = state,
            action = action,
            errors = errors,
            events = { event -> signUpViewModel.setEvent(event) },
            onNavigateToMain = onNavigateToMain
        )
    }
}

fun NavController.navigateToSignUpByOtp(navArgs: SignUpByOtpNavArgs) {
    navigate(SignUpByOtpDestination(navArgs))
}