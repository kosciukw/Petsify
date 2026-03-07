package pl.kosciukw.petsify.feature.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.feature.home.presentation.ui.HomeScreen
import pl.kosciukw.petsify.feature.home.presentation.ui.HomeViewModel

@Serializable
private data object HomeDestination

fun NavGraphBuilder.homeScreen() {
    composable<HomeDestination> {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val state = homeViewModel.state.value
        val action = homeViewModel.action
        val errors = homeViewModel.errors

        HomeScreen(
            state = state,
            action = action,
            errors = errors,
            events = { event -> homeViewModel.setEvent(event) }
        )
    }
}

fun NavController.navigateToHome() {
    navigate(HomeDestination) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
