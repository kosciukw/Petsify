package pl.kosciukw.petsify.feature.login.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.login.presentation.LoginAction
import pl.kosciukw.petsify.feature.login.presentation.LoginEvent
import pl.kosciukw.petsify.feature.login.presentation.LoginState
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.LoginStrings
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.BodyM
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
    val snackbarHostState = remember { SnackbarHostState() }
    val errorQueue = remember { mutableStateListOf<UIComponent>() }

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

    LaunchedEffect(errorQueue.size) {
        val head = errorQueue.firstOrNull()
        if (head is UIComponent.ToastSimple) {
            snackbarHostState.showSnackbar(head.title)
            errorQueue.removeAt(0)
        }
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

                LoginTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingS),
                    label = strings.emailLabel,
                    text = state.inputEmail,
                    onTextChange = { events(LoginEvent.OnEmailTextChanged(it)) },
                    trailingIcon = assets.pawPainter,
                    errorMessage = strings.emailError,
                    isError = state.isEmailValidationErrorEnabled
                )

                LoginTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingS),
                    label = strings.passwordLabel,
                    text = state.inputPassword,
                    onTextChange = { events(LoginEvent.OnPasswordTextChanged(it)) },
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
                    onClick = onGoogleLoginClick
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            Snackbar(
                modifier = Modifier.padding(BodyM),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                action = {
                    TextButton(onClick = { data.dismiss() }) {
                        Text(commonStrings.okButton)
                    }
                }
            ) {
                Text(data.visuals.message)
            }
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

@Composable
private fun LoginTextField(
    modifier: Modifier,
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    trailingIcon: Painter?,
    errorMessage: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var isPasswordVisible by rememberSaveable(isPassword) { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        label = { Text(label) },
        trailingIcon = {
            trailingIcon?.let {
                PasswordToggleIcon(
                    painter = it,
                    isPassword = isPassword,
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage)
            }
        },
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = when {
            isPassword && !isPasswordVisible -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
    )
}

@Composable
private fun PasswordToggleIcon(
    painter: Painter,
    isPassword: Boolean,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    val iconModifier = Modifier
        .size(24.dp)
        .padding(4.dp)

    if (!isPassword) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = iconModifier
        )
        return
    }

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = iconModifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onToggleVisibility
        ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
            modifier = Modifier.matchParentSize()
        )

        if (!isPasswordVisible) {
            HiddenPasswordBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = (-8).dp)
            )
        }
    }
}

@Composable
private fun HiddenPasswordBadge(
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.surface
    val contentColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .size(12.dp)
            .background(containerColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(8.dp)) {
            val strokeWidth = 1.dp.toPx()
            val shackleRadius = size.width * 0.22f
            val shackleCenterY = size.height * 0.33f
            val bodyTop = size.height * 0.42f

            drawArc(
                color = contentColor,
                startAngle = 200f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.28f,
                    y = shackleCenterY - shackleRadius
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width * 0.44f,
                    height = size.height * 0.38f
                ),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawRoundRect(
                color = contentColor,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.24f,
                    y = bodyTop
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width * 0.52f,
                    height = size.height * 0.34f
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.5.dp.toPx())
            )
        }
    }
}

@Composable
private fun LoginWithGoogleButton(
    modifier: Modifier = Modifier,
    label: String,
    painter: Painter?,
    contentDescription: String = label,
    iconPadding: Dp = paddingS,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 1.dp,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    isButtonEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier.border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(22.dp)
        ),
        enabled = isButtonEnabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            painter?.let {
                Image(
                    painter = it,
                    contentDescription = contentDescription,
                    modifier = Modifier.padding(end = iconPadding)
                )
            }
            Text(
                style = TextBoldS,
                text = label,
                color = textColor
            )
        }
    }
}
