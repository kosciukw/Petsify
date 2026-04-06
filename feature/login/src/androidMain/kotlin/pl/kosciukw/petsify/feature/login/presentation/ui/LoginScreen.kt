package pl.kosciukw.petsify.feature.login.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.login.presentation.LoginAction
import pl.kosciukw.petsify.feature.login.presentation.LoginEvent
import pl.kosciukw.petsify.feature.login.presentation.LoginState
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.LoginStrings

@Composable
internal fun LoginScreen(
    state: LoginState,
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (LoginEvent) -> Unit,
    action: Flow<LoginAction>,
    strings: LoginStrings,
    commonStrings: CommonScreenStrings,
    context: Context
) {
    LoginScreenContent(
        state = state,
        onNavigateToMain = onNavigateToMain,
        onNavigateToSignUp = onNavigateToSignUp,
        errors = errors,
        events = events,
        action = action,
        strings = strings,
        commonStrings = commonStrings,
        assets = rememberAndroidLoginScreenAssets(),
        onGoogleLoginClick = {}
    )
}
