package pl.kosciukw.petsify.feature.signup.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.SignUpStrings
import pl.kosciukw.petsify.shared.ui.components.checkbox.CheckboxRow
import pl.kosciukw.petsify.shared.ui.components.input.FilledTextField
import pl.kosciukw.petsify.shared.ui.components.scaffold.ScreenScaffold
import pl.kosciukw.petsify.shared.ui.components.spacer.VerticalSpacer
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@Composable
fun SignUpScreen(
    state: SignUpState,
    onNavigateToOtp: (SignUpByOtpNavArgs) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpEvent) -> Unit,
    action: Flow<SignUpAction>,
    strings: SignUpStrings,
    commonStrings: CommonScreenStrings
) {
    val currentAction by rememberUpdatedState(action)

    DisposableEffect(Unit) {
        onDispose { events(SignUpEvent.OnScreenDisposed) }
    }

    LaunchedEffect(Unit) {
        currentAction.collect { act ->
            when (act) {
                is SignUpAction.Navigation.NavigateToOtp -> onNavigateToOtp(act.navArgs)
                SignUpAction.Navigation.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    ScreenScaffold(
        title = strings.title,
        onNavigateUp = onNavigateUp,
        errors = errors,
        progressBarState = state.progressBarState,
        commonStrings = commonStrings
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingXXL)
                .verticalScroll(rememberScrollState())
        ) {
            VerticalSpacer(96.dp)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = strings.intro,
                style = TextRegularS,
                textAlign = TextAlign.Center
            )

            VerticalSpacer(32.dp)

            FilledTextField(
                value = state.inputName,
                onValueChange = { events(SignUpEvent.OnNameTextChanged(it)) },
                label = strings.nameLabel,
                errorMessage = strings.nameError,
                isError = state.isNameValidationErrorEnabled
            )

            VerticalSpacer(12.dp)

            FilledTextField(
                value = state.inputEmail,
                onValueChange = { events(SignUpEvent.OnEmailTextChanged(it)) },
                label = strings.emailLabel,
                errorMessage = strings.emailError,
                isError = state.isEmailValidationErrorEnabled,
                keyboardType = KeyboardType.Email
            )

            VerticalSpacer(12.dp)

            FilledTextField(
                value = state.inputPassword,
                onValueChange = { events(SignUpEvent.OnPasswordChanged(it)) },
                label = strings.passwordLabel,
                errorMessage = strings.passwordError,
                isError = state.isPasswordValidationErrorEnabled,
                keyboardType = KeyboardType.Password
            )

            VerticalSpacer(12.dp)

            FilledTextField(
                value = state.inputConfirmPassword,
                onValueChange = { events(SignUpEvent.OnConfirmPasswordChanged(it)) },
                label = strings.confirmPasswordLabel,
                errorMessage = strings.confirmPasswordError,
                isError = state.isConfirmPasswordValidationErrorEnabled,
                keyboardType = KeyboardType.Password
            )

            VerticalSpacer(16.dp)

            CheckboxRow(
                checked = state.isTermsAccepted,
                text = strings.termsConsent,
                onCheckedChange = { events(SignUpEvent.OnTermsAcceptedChanged(it)) }
            )

            VerticalSpacer(16.dp)

            CheckboxRow(
                checked = state.isMarketingAccepted,
                text = strings.marketingConsent,
                onCheckedChange = { events(SignUpEvent.OnMarketingAcceptedChanged(it)) }
            )

            VerticalSpacer(32.dp)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
                onClick = { events(SignUpEvent.OnConfirmButtonClicked) },
                enabled = state.isSignUpButtonStateEnabled
            ) {
                Text(
                    text = strings.submitButton,
                    style = TextBoldS,
                    color = PureWhite
                )
            }

            VerticalSpacer(32.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = strings.haveAccount,
                    style = TextRegularS
                )

                Text(
                    text = " ${strings.loginAction}",
                    style = TextBoldS,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.clickable(onClick = onNavigateToLogin)
                )
            }
        }
    }
}
