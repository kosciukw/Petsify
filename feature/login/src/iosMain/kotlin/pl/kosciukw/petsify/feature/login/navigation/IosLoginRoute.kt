package pl.kosciukw.petsify.feature.login.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform
import pl.kosciukw.petsify.feature.login.presentation.ui.LoginScreenContent
import pl.kosciukw.petsify.feature.login.presentation.ui.LoginViewModel
import pl.kosciukw.petsify.feature.login.presentation.ui.rememberLoginScreenAssets
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider

@Composable
fun IosLoginRoute(
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val loginViewModel = remember { KoinPlatform.getKoin().get<LoginViewModel>() }
    val stringsProvider = remember { KoinPlatform.getKoin().get<FeatureStringsProvider>() }
    val state by loginViewModel.state.collectAsState()

    DisposableEffect(loginViewModel) {
        onDispose { loginViewModel.clear() }
    }

    LoginScreenContent(
        state = state,
        onNavigateToMain = onNavigateToMain,
        onNavigateToSignUp = onNavigateToSignUp,
        errors = loginViewModel.errors,
        events = loginViewModel::setEvent,
        action = loginViewModel.action,
        strings = stringsProvider.login(),
        commonStrings = stringsProvider.common(),
        assets = rememberLoginScreenAssets(),
        onGoogleLoginClick = {}
    )
}
