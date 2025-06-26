package pl.kosciukw.petsify.feature.signup.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.collectLatest
import pl.kosciukw.petsify.feature.signup.presentation.*
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.base.BaseScreen
import pl.kosciukw.petsify.shared.ui.components.button.ButtonRegular
import pl.kosciukw.petsify.shared.ui.components.checkbox.CheckBoxText
import pl.kosciukw.petsify.shared.ui.components.input.EditText
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.spacer.*
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig
import pl.kosciukw.petsify.shared.ui.theme.*
import pl.kosciukw.petsify.shared.ui.R as SharedR

@Composable
internal fun SignUpScreen(
    state: SignUpState,
    onNavigateToOtp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpEvent) -> Unit,
    action: Flow<SignUpAction>
) {
    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentAction.collectLatest { act ->
            when (act) {
                is SignUpAction.Navigation.NavigateToOtp -> onNavigateToOtp()
                is SignUpAction.Navigation.NavigateToLogin -> onNavigateToLogin()
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
            titleToolbar = stringResource(SharedR.string.sign_up_screen_header),
            startIconToolbar = ImageVector.vectorResource(id = SharedR.drawable.ic_arrow_back),
            onClickStartIconToolbar = onNavigateUp
        ),
        content = {
            SignUpForm(
                inputName = state.inputName,
                inputEmail = state.inputEmail,
                inputPassword = state.inputPassword,
                inputConfirmPassword = state.inputConfirmPassword,
                isNameError = state.isNameValidationErrorEnabled,
                isEmailError = state.isEmailValidationErrorEnabled,
                isPasswordError = state.isPasswordValidationErrorEnabled,
                isConfirmPasswordError = state.isConfirmPasswordValidationErrorEnabled,
                isTermsAccepted = state.isTermsAccepted,
                isMarketingAccepted = state.isMarketingAccepted,
                isSignUpButtonEnabled = state.isSignUpButtonStateEnabled,
                onNameChange = { events(SignUpEvent.OnNameTextChanged(it)) },
                onEmailChange = { events(SignUpEvent.OnEmailTextChanged(it)) },
                onPasswordChange = { events(SignUpEvent.OnPasswordChanged(it)) },
                onConfirmPasswordChange = { events(SignUpEvent.OnConfirmPasswordChanged(it)) },
                onTermsChanged = { events(SignUpEvent.OnTermsAcceptedChanged(it)) },
                onMarketingChanged = { events(SignUpEvent.OnMarketingAcceptedChanged(it)) },
                onSignUpClick = { events(SignUpEvent.OnConfirmButtonClicked) },
                onLoginClick = { events(SignUpEvent.OnLoginButtonClicked) }
            )
        }
    )
}

@Composable
private fun SignUpForm(
    inputName: String,
    inputEmail: String,
    inputPassword: String,
    inputConfirmPassword: String,
    isNameError: Boolean,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isConfirmPasswordError: Boolean,
    isTermsAccepted: Boolean,
    isMarketingAccepted: Boolean,
    isSignUpButtonEnabled: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTermsChanged: (Boolean) -> Unit,
    onMarketingChanged: (Boolean) -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingXXL)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer96dp()

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = SharedR.string.sign_up_screen_please_enter_data),
            style = TextRegularS,
            textAlign = TextAlign.Center
        )

        Spacer32dp()

        EditText(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = SharedR.string.sign_up_screen_name),
            text = inputName,
            onTextChange = onNameChange,
            isErrorMessageEnabled = isNameError,
            errorMessage = stringResource(id = SharedR.string.name_validation_error)
        )

        EditText(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = SharedR.string.sign_up_screen_email),
            text = inputEmail,
            onTextChange = onEmailChange,
            isErrorMessageEnabled = isEmailError,
            errorMessage = stringResource(id = SharedR.string.email_validation_error)
        )

        EditText(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = SharedR.string.sign_up_screen_password),
            text = inputPassword,
            onTextChange = onPasswordChange,
            isErrorMessageEnabled = isPasswordError,
            errorMessage = stringResource(id = SharedR.string.create_password_validation_error)
        )

        EditText(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = SharedR.string.sign_up_screen_confirm_password),
            text = inputConfirmPassword,
            onTextChange = onConfirmPasswordChange,
            isErrorMessageEnabled = isConfirmPasswordError,
            errorMessage = stringResource(id = SharedR.string.confirm_password_validation_error)
        )

        Spacer16dp()

        CheckBoxText(
            isChecked = isTermsAccepted,
            text = stringResource(id = SharedR.string.sign_up_screen_confirm_accept_terms_and_conditions),
            onCheckedChange = onTermsChanged,
            textStyle = TextRegularS.copy(textAlign = TextAlign.Start)
        )

        Spacer16dp()

        CheckBoxText(
            isChecked = isMarketingAccepted,
            text = stringResource(id = SharedR.string.sign_up_screen_confirm_accept_marketing),
            onCheckedChange = onMarketingChanged,
            textStyle = TextRegularS.copy(textAlign = TextAlign.Start)
        )

        Spacer32dp()

        ButtonRegular(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = paddingM),
            style = TextBoldS,
            buttonColors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
            onClick = onSignUpClick,
            label = stringResource(id = SharedR.string.sign_up_screen_sign_up),
            textColor = PureWhite,
            isButtonEnabled = isSignUpButtonEnabled
        )

        Spacer32dp()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = SharedR.string.sign_up_screen_have_an_account),
                style = TextRegularS
            )

            Spacer8dp()

            Text(
                text = stringResource(id = SharedR.string.sign_up_screen_login),
                style = TextBoldS,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSignUpScreen() {
    PetsifyTheme {
        SignUpScreen(
            state = SignUpState(
                inputName = "John",
                inputEmail = "john@example.com",
                inputPassword = "pass123",
                inputConfirmPassword = "pass123",
                isNameValidationErrorEnabled = false,
                isEmailValidationErrorEnabled = false,
                isPasswordValidationErrorEnabled = false,
                isConfirmPasswordValidationErrorEnabled = false,
                isTermsAccepted = true,
                isMarketingAccepted = false,
                isSignUpButtonStateEnabled = true,
                progressBarState = ProgressBarState.Idle
            ),
            onNavigateToOtp = {},
            onNavigateUp = {},
            errors = flowOf(),
            events = {},
            onNavigateToLogin = {},
            action = flowOf()
        )
    }
}