package pl.kosciukw.petsify.feature.otp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpState
import pl.kosciukw.petsify.shared.ui.R
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.base.BaseScreen
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig


@Composable
internal fun SignUpByOtpScreen(
    state: SignUpByOtpState,
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpByOtpEvent) -> Unit,
    action: Flow<SignUpByOtpAction>
) {
    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentAction.collectLatest { act ->
            when (act) {
                is SignUpByOtpAction.Navigation.NavigateToMain -> onNavigateToMain()
            }
        }
    }

    BaseScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        errors = errors,
        progressBarState = state.progressBarState,
        toolbarConfig = ToolbarConfig(
            titleToolbar = stringResource(R.string.sign_up_screen_header),
            startIconToolbar = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
            onClickStartIconToolbar = onNavigateUp
        ),
        content = {}
    )
}