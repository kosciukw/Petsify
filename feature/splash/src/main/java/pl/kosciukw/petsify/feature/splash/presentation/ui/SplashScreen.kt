package pl.kosciukw.petsify.feature.splash.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
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

//    BaseScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background),
//        errors = errors,
//        progressBarState = state.progressBarState,
//        toolbarConfig = ToolbarConfig(
//            titleToolbar = stringResource(R.string.sign_up_screen_header),
//            startIconToolbar = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
//            onClickStartIconToolbar = onNavigateUp
//        ),
//        content = {
//
//
//        }
}