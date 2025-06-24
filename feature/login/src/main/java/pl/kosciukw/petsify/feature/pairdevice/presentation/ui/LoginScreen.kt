package pl.kosciukw.petsify.feature.pairdevice.presentation.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pl.kosciukw.petsify.feature.login.R
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginAction
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginEvent
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginState
import pl.kosciukw.petsify.shared.ui.components.spacer.*
import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.extensions.makeToast
import pl.kosciukw.petsify.shared.ui.components.image.BackgroundImage
import pl.kosciukw.petsify.shared.ui.components.button.ButtonRegular
import pl.kosciukw.petsify.shared.ui.components.button.ButtonText
import pl.kosciukw.petsify.shared.ui.components.base.BaseScreen
import pl.kosciukw.petsify.shared.ui.components.input.EditText
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.secure.PasswordInput
import pl.kosciukw.petsify.shared.ui.theme.*
import pl.kosciukw.petsify.shared.utils.empty
import pl.kosciukw.petsify.shared.ui.R as SharedR

@Composable
internal fun LoginScreen(
    state: LoginState,
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (LoginEvent) -> Unit,
    action: Flow<LoginAction>,
    context: Context
) {
    val currentActionFlow by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentActionFlow.collect { action ->
            when (action) {
                is LoginAction.Navigation.NavigateToMain -> onNavigateToMain()
                is LoginAction.Navigation.NavigateToSignup -> onNavigateToSignUp()
                else -> Unit
            }
        }
    }

    BaseScreen(
        errors = errors,
        progressBarState = state.progressBarState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Header(modifier = Modifier.fillMaxWidth())
                Spacer32dp()
                LoginForm(
                    inputEmail = state.inputEmail,
                    inputPassword = state.inputPassword,
                    isEmailError = state.isEmailValidationErrorEnabled,
                    isPasswordError = state.isPasswordValidationErrorEnabled,
                    isLoginEnabled = state.isLoginButtonEnabled,
                    onLoginClick = {
                        events(
                            LoginEvent.Login(
                                state.inputEmail,
                                state.inputPassword
                            )
                        )
                    },
                    onEmailChange = { email ->
                        events(LoginEvent.OnEmailTextChanged(email))
                    },
                    onPasswordChange = { events(LoginEvent.OnPasswordTextChanged(it)) },
                    onNavigateToSignUpClick = { events(LoginEvent.OnNavigateToSignUpClicked) },
                    context = context
                )
                Spacer32dp()
            }
        }
    )
}

@Composable
private fun LoginForm(
    context: Context,
    inputEmail: String,
    inputPassword: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isLoginEnabled: Boolean,
    onLoginClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNavigateToSignUpClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = paddingL)
    ) {
        Text(
            text = stringResource(id = SharedR.string.login_screen_welcomd_our_community_today),
            style = TextBoldXL,
            color = GoshawkGrey,
            modifier = Modifier.padding(paddingM)
        )

        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingS),
            trailingIcon = ImageVector.vectorResource(id = R.drawable.ic_paw),
            label = stringResource(id = SharedR.string.login_screen_email_field),
            text = inputEmail,
            onTextChange = onEmailChange,
            isErrorMessageEnabled = isEmailError,
            errorMessage = stringResource(id = SharedR.string.email_validation_error),
        )

        PasswordInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingS),
            inputType = KeyboardType.Password,
            trailingIcon = ImageVector.vectorResource(id = R.drawable.ic_bone),
            label = stringResource(id = SharedR.string.login_screen_password_field),
            text = inputPassword,
            onTextChange = onPasswordChange,
            errorMessage = stringResource(id = SharedR.string.password_validation_error),
            isErrorMessageEnabled = isPasswordError
        )

        Text(
            text = stringResource(id = SharedR.string.login_screen_forgot_password),
            style = TextRegularS.copy(textAlign = TextAlign.End, fontSize = TextS),
            modifier = Modifier
                .padding(vertical = paddingM)
                .fillMaxWidth()
        )

        ButtonRegular(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = paddingM),
            style = TextBoldS,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            onClick = onLoginClick,
            label = stringResource(id = SharedR.string.login_screen_login_button),
            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            isButtonEnabled = isLoginEnabled
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = paddingXL)
        ) {
            Text(
                text = stringResource(id = SharedR.string.login_screen_login_new_to_petsify),
                style = TextRegularS
            )

            ButtonText(
                modifier = Modifier.padding(start = paddingS),
                onClick = onNavigateToSignUpClick,
                label = stringResource(id = SharedR.string.login_screen_signup),
                style = TextBoldS,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Text(
            text = stringResource(id = SharedR.string.login_screen_or),
            style = TextRegularS,
            modifier = Modifier.padding(vertical = paddingL)
        )

        LoginWithGoogleButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = paddingM),
            style = TextBoldS,
            onClick = {
                context.makeToast("Login with google button clicked")
            },
            label = stringResource(id = SharedR.string.login_screen_google_login_button),
            isButtonEnabled = true,
            painter = painterResource(id = R.drawable.ic_google)
        )
    }
}

@Composable
private fun Header(modifier: Modifier) {
    Box(modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer)) {
        Image(
            painter = painterResource(id = R.drawable.login_screen_background),
            contentDescription = String.empty(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingXXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = SharedR.string.app_name),
                style = TextPrimary,
                color = GoshawkGrey,
                modifier = Modifier.padding(top = paddingGapM)
            )
            BackgroundImage(
                modifier = Modifier.padding(top = paddingS),
                imageId = R.drawable.login_screen_header_image
            )
            Spacer8dp()
        }
    }
}

@Composable
private fun LoginWithGoogleButton(
    modifier: Modifier = Modifier,
    style: TextStyle = TextBoldS,
    label: String,
    painter: Painter,
    contentDescription: String = label,
    iconPadding: Dp = paddingS,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 1.dp,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
    isButtonEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = modifier
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(22.dp)
            )
            .height(50.dp)
            .fillMaxWidth(),
        enabled = isButtonEnabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier.padding(end = iconPadding)
            )
            Text(
                style = style,
                text = label,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    PetsifyTheme {
        LoginScreen(
            onNavigateToMain = {},
            onNavigateToSignUp = {},
            errors = flowOf(),
            events = {},
            action = flowOf(),
            state = LoginState(
                inputEmail = "test@example.com",
                inputPassword = "password123",
                progressBarState = ProgressBarState.Idle,
                isEmailValidationErrorEnabled = true,
                isPasswordValidationErrorEnabled = true,
                networkState = NetworkState.Established,
                isLoginButtonEnabled = true
            ),
            context = LocalContext.current
        )
    }
}