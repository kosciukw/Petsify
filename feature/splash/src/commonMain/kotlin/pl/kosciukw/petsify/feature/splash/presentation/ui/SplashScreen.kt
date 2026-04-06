package pl.kosciukw.petsify.feature.splash.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import pl.kosciukw.petsify.feature.splash.presentation.SplashAction
import pl.kosciukw.petsify.feature.splash.presentation.SplashEvent
import pl.kosciukw.petsify.feature.splash.presentation.SplashState
import pl.kosciukw.petsify.shared.presentation.UIComponent

@Composable
fun SplashScreen(
    state: SplashState,
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SplashEvent) -> Unit,
    action: Flow<SplashAction>
) {

    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentAction.collectLatest { act ->
            when (act) {
                SplashAction.Navigation.NavigateToMain -> onNavigateToMain()
                SplashAction.Navigation.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondaryContainer)
        }
    }
}
