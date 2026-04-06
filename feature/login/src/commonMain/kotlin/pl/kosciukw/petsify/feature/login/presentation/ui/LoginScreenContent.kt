package pl.kosciukw.petsify.feature.login.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import pl.kosciukw.petsify.feature.login.presentation.LoginAction
import pl.kosciukw.petsify.feature.login.presentation.LoginEvent
import pl.kosciukw.petsify.feature.login.presentation.LoginState
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.LoginStrings
import pl.kosciukw.petsify.shared.ui.components.button.OutlinedIconButton
import pl.kosciukw.petsify.shared.ui.components.input.PasswordAwareTextField
import pl.kosciukw.petsify.shared.ui.components.snackbar.AppSnackbar
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextBoldXL
import pl.kosciukw.petsify.shared.ui.theme.TextPrimary
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.TextS
import pl.kosciukw.petsify.shared.ui.theme.paddingGapM
import pl.kosciukw.petsify.shared.ui.theme.paddingL
import pl.kosciukw.petsify.shared.ui.theme.paddingM
import pl.kosciukw.petsify.shared.ui.theme.paddingS
import pl.kosciukw.petsify.shared.ui.theme.paddingXL
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@Composable
fun LoginScreenContent(
    state: LoginState,
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (LoginEvent) -> Unit,
    action: Flow<LoginAction>,
    strings: LoginStrings,
    commonStrings: CommonScreenStrings,
    assets: LoginScreenAssets,
    onGoogleLoginClick: () -> Unit
) {
    val currentActionFlow by rememberUpdatedState(action)
    val errorQueue = remember { mutableStateListOf<UIComponent>() }
    var googleLoginFeedback by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        currentActionFlow.collect { currentAction ->
            when (currentAction) {
                LoginAction.Navigation.NavigateToMain -> onNavigateToMain()
                LoginAction.Navigation.NavigateToSignup -> onNavigateToSignUp()
            }
        }
    }

    LaunchedEffect(errors) {
        errors.collect { errorQueue.add(it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Header(
                modifier = Modifier.fillMaxWidth(),
                appName = strings.appName,
                assets = assets
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = paddingL)
            ) {
                Text(
                    text = strings.welcome,
                    style = TextBoldXL,
                    color = GoshawkGrey,
                    modifier = Modifier.padding(paddingM)
                )

                PasswordAwareTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingS),
                    label = strings.emailLabel,
                    value = state.inputEmail,
                    onValueChange = { events(LoginEvent.OnEmailTextChanged(it)) },
                    trailingIcon = assets.pawPainter,
                    errorMessage = strings.emailError,
                    isError = state.isEmailValidationErrorEnabled
                )

                PasswordAwareTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingS),
                    label = strings.passwordLabel,
                    value = state.inputPassword,
                    onValueChange = { events(LoginEvent.OnPasswordTextChanged(it)) },
                    trailingIcon = assets.bonePainter,
                    errorMessage = strings.passwordError,
                    isError = state.isPasswordValidationErrorEnabled,
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )

                Text(
                    text = strings.forgotPassword,
                    style = TextRegularS.copy(textAlign = TextAlign.End, fontSize = TextS),
                    modifier = Modifier
                        .padding(vertical = paddingM)
                        .fillMaxWidth()
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = paddingM),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    onClick = {
                        events(LoginEvent.Login(state.inputEmail, state.inputPassword))
                    },
                    enabled = state.isLoginButtonEnabled
                ) {
                    Text(
                        text = strings.loginButton,
                        style = TextBoldS,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = paddingXL)
                ) {
                    Text(
                        text = strings.newToPetsify,
                        style = TextRegularS
                    )

                    Text(
                        text = strings.signUp,
                        style = TextBoldS,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(start = paddingS)
                            .clickable(onClick = onNavigateToSignUp)
                    )
                }

                Text(
                    text = strings.orLabel,
                    style = TextRegularS,
                    modifier = Modifier.padding(vertical = paddingL)
                )

                LoginWithGoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = paddingM),
                    label = strings.googleButton,
                    painter = assets.googlePainter,
                    isButtonEnabled = true,
                    onClick = {
                        onGoogleLoginClick()
                        googleLoginFeedback = strings.googleLoginFeedback
                    }
                )
            }
        }

        if (state.progressBarState != ProgressBarState.Idle) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondaryContainer)
            }
        }

        val toast = errorQueue.firstOrNull() as? UIComponent.ToastSimple
        if (toast != null) {
            AppSnackbar(
                title = toast.title,
                actionLabel = commonStrings.okButton,
                onDismiss = { errorQueue.removeAt(0) },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        val googleFeedback = googleLoginFeedback
        if (googleFeedback != null) {
            AppSnackbar(
                title = googleFeedback,
                actionLabel = commonStrings.okButton,
                onDismiss = { googleLoginFeedback = null },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    when (val head = errorQueue.firstOrNull()) {
        is UIComponent.Dialog -> {
            AlertDialog(
                onDismissRequest = { errorQueue.removeAt(0) },
                confirmButton = {
                    TextButton(onClick = { errorQueue.removeAt(0) }) {
                        Text(commonStrings.okButton)
                    }
                },
                title = { Text(head.title) },
                text = { Text(head.description) }
            )
        }

        is UIComponent.None -> {
            LaunchedEffect(head) {
                errorQueue.removeAt(0)
            }
        }

        else -> Unit
    }
}

@Composable
private fun LoginWithGoogleButton(
    modifier: Modifier = Modifier,
    label: String,
    painter: Painter?,
    isButtonEnabled: Boolean,
    onClick: () -> Unit
) {
    OutlinedIconButton(
        label = label,
        onClick = onClick,
        modifier = modifier,
        painter = painter,
        isButtonEnabled = isButtonEnabled
    )
}

@Composable
private fun Header(
    modifier: Modifier,
    appName: String,
    assets: LoginScreenAssets
) {
    Box(modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)) {
        assets.headerBackgroundPainter?.let { backgroundPainter ->
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingXXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = appName,
                style = TextPrimary,
                color = GoshawkGrey,
                modifier = Modifier.padding(top = paddingGapM)
            )
            assets.headerPainter?.let { painter ->
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.padding(top = paddingS)
                )
            }
        }
    }
}
