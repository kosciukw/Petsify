package pl.kosciukw.petsify.feature.signup.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpScreen
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpViewModel

@Serializable
private data object SignUpDestination

fun NavGraphBuilder.signUpScreen(
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable<SignUpDestination> {

        val signUpViewModel: SignUpViewModel = hiltViewModel()
        val state = signUpViewModel.state.value
        val action = signUpViewModel.action
        val errors = signUpViewModel.errors

        SignUpScreen(
            onNavigateToMain = onNavigateToMain,
            onNavigateUp = onNavigateUp,
            state = state,
            action = action,
            errors = errors,
            events = { event -> signUpViewModel.setEvent(event) })
    }
}

fun NavController.navigateToSignUp() {
    navigate(SignUpDestination)
}