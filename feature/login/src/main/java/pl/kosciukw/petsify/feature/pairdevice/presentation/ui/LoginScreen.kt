package pl.kosciukw.petsify.feature.pairdevice.presentation.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import pl.kosciukw.petsify.shared.components.Spacer8dp
import pl.kosciukw.petsify.shared.components.Spacer32dp
import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.extensions.makeToast
import pl.kosciukw.petsify.shared.ui.components.BackgroundImage
import pl.kosciukw.petsify.shared.ui.components.ButtonRegular
import pl.kosciukw.petsify.shared.ui.components.ButtonText
import pl.kosciukw.petsify.shared.ui.components.DefaultScreenUI
import pl.kosciukw.petsify.shared.ui.components.EditText
import pl.kosciukw.petsify.shared.ui.components.UIComponent
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.secure.PasswordInput
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
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
    LaunchedEffect(action) {
        action.collect { action ->
            when (action) {
                is LoginAction.Navigation.NavigateToMain -> {
                    onNavigateToMain()
                }

                else -> {
                    //no-op
                }
            }
        }
    }

    DefaultScreenUI(
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
                    onLoginButtonClicked = {
                        events(
                            LoginEvent.Login(
                                state.inputEmail,
                                state.inputPassword
                            )
                        )
                    },
                    onNavigateToSignUp = onNavigateToSignUp,
                    modifier = Modifier.fillMaxWidth(),
                    onEmailTextChanged = { email ->
                        events(LoginEvent.OnEmailTextChanged(email))
                    },
                    onPasswordTextChanged = { password ->
                        events(LoginEvent.OnPasswordTextChanged(password))
                    },
                    state = state,
                    context = context
                )
            }
        }
    )
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
private fun LoginForm(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    state: LoginState,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = paddingL)
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
            text = state.inputEmail,
            onTextChange = { email -> onEmailTextChanged(email) },
            isErrorMessageEnabled = state.isEmailValidationErrorEnabled,
            errorMessage = stringResource(id = SharedR.string.login_screen_email_validation_error),
        )

        PasswordInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingS),
            inputType = KeyboardType.Password,
            trailingIcon = ImageVector.vectorResource(id = R.drawable.ic_bone),
            label = stringResource(id = SharedR.string.login_screen_password_field),
            text = state.inputPassword,
            onTextChange = { password -> onPasswordTextChanged(password) },
            errorMessage = stringResource(id = SharedR.string.login_screen_password_validation_error),
            isErrorMessageEnabled = state.isPasswordValidationErrorEnabled
        )

        Text(
            text = stringResource(id = SharedR.string.login_screen_forgot_password),
            style = TextRegularS.copy(textAlign = TextAlign.End, fontSize = TextS),
            modifier = Modifier
                .padding(vertical = paddingM)
                .fillMaxWidth()
        )

        // Login Button
        ButtonRegular(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = paddingM),
            style = TextBoldS,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = BlackLiquorice
            ),
            onClick = onLoginButtonClicked,
            label = stringResource(id = SharedR.string.login_screen_login_button),
            textColor = PureWhite,
            isButtonEnabled = state.isLoginButtonEnabled
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
                onClick = onNavigateToSignUp,
                label = stringResource(id = SharedR.string.login_screen_signup),
                style = TextBoldS,
                textColor = GoshawkGrey
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
                context.makeToast(message = "Login with google button clicked")
            },
            label = stringResource(id = SharedR.string.login_screen_google_login_button),
            isButtonEnabled = true,
            painter = painterResource(id = R.drawable.ic_google)
        )

        Spacer32dp()
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