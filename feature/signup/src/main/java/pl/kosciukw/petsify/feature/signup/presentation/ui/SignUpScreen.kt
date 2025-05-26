package pl.kosciukw.petsify.feature.signup.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.shared.components.Spacer16dp
import pl.kosciukw.petsify.shared.components.Spacer32dp
import pl.kosciukw.petsify.shared.components.Spacer8dp
import pl.kosciukw.petsify.shared.components.Spacer96dp
import pl.kosciukw.petsify.shared.ui.components.ButtonRegular
import pl.kosciukw.petsify.shared.ui.components.DefaultScreenUI
import pl.kosciukw.petsify.shared.ui.components.EditText
import pl.kosciukw.petsify.shared.ui.components.UIComponent
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.paddingM
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL
import pl.kosciukw.petsify.shared.ui.R as SharedR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    state: SignUpState,
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpEvent) -> Unit,
    action: Flow<SignUpAction>
) {
    DefaultScreenUI(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        errors = errors,
        progressBarState = state.progressBarState,
        toolbarConfig = ToolbarConfig(
            titleToolbar = stringResource(SharedR.string.sign_up_screen_header),
            startIconToolbar = ImageVector.vectorResource(id = SharedR.drawable.ic_arrow_back),
            onClickStartIconToolbar = { onNavigateUp() }
        ),
        content = {
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
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )

                Spacer16dp()

                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = SharedR.string.sign_up_screen_name),
                    text = state.inputName,
                    onTextChange = { name ->
                        events(SignUpEvent.OnNameTextChanged(name))
                    },
                    isErrorMessageEnabled = state.isNameValidationErrorEnabled,
                    errorMessage = stringResource(id = SharedR.string.name_validation_error)
                )

                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = SharedR.string.sign_up_screen_email),
                    text = state.inputEmail,
                    onTextChange = { email -> events(SignUpEvent.OnEmailTextChanged(email)) },
                    isErrorMessageEnabled = state.isEmailValidationErrorEnabled,
                    errorMessage = stringResource(id = SharedR.string.email_validation_error)
                )

                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = SharedR.string.sign_up_screen_password),
                    text = state.inputPassword,
                    onTextChange = { password -> events(SignUpEvent.OnPasswordTextChanged(password)) },
                    isErrorMessageEnabled = state.isPasswordValidationErrorEnabled,
                    errorMessage = stringResource(id = SharedR.string.password_validation_error)
                )

                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = SharedR.string.sign_up_screen_confirm_password),
                    text = state.inputConfirmPassword,
                    onTextChange = { confirmPassword ->
                        events(SignUpEvent.OnConfirmPasswordTextChanged(confirmPassword))
                    },
                    isErrorMessageEnabled = state.isConfirmPasswordValidationErrorEnabled,
                    errorMessage = stringResource(id = SharedR.string.password_validation_error)
                )

                Spacer16dp()

                Row {
                    TermsAndConditionsCheckbox(isChecked = false) {

                    }
                    Spacer8dp()
                    Text(
                        text = stringResource(id = SharedR.string.sign_up_screen_confirm_accept_terms_and_conditions),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer32dp()

                ButtonRegular(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = paddingM),
                    style = TextBoldS,
                    buttonColors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
                    onClick = { },
                    label = stringResource(id = SharedR.string.sign_up_screen_sign_up),
                    textColor = PureWhite,
                    isButtonEnabled = state.isSignUpButtonStateEnabled
                )

                Spacer32dp()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = SharedR.string.sign_up_screen_have_an_account),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer8dp()
                    Text(
                        text = stringResource(id = SharedR.string.sign_up_screen_login),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    )
}

@Composable
fun TermsAndConditionsCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(8.dp))
//        TextWithLinks(
//            text = "Akceptuję Regulamin i Politykę Prywatności",
//            onClickTerms = { /* TODO: Open Terms screen */ },
//            onClickPrivacy = { /* TODO: Open Privacy screen */ }
//        )
    }
}

@Preview
@Composable
private fun PreviewSignUpScreen() {
    PetsifyTheme {
//        SignUpScreen(
//            onNavigateToMain = {},
//            onNavigateUp = {}
//        )
    }
}