package pl.kosciukw.petsify.feature.otp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.components.OtpInput
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpState
import pl.kosciukw.petsify.feature.signup.presentation.ui.PetsifyScreenScaffold
import pl.kosciukw.petsify.feature.signup.presentation.ui.VerticalSpacer
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.OtpStrings
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@Composable
fun SignUpByOtpScreen(
    state: SignUpByOtpState,
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpByOtpEvent) -> Unit,
    action: Flow<SignUpByOtpAction>,
    strings: OtpStrings,
    commonStrings: CommonScreenStrings
) {
    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentAction.collect { act ->
            when (act) {
                SignUpByOtpAction.Navigation.NavigateToMain -> onNavigateToMain()
            }
        }
    }

    PetsifyScreenScaffold(
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            VerticalSpacer(96.dp)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = strings.intro,
                style = TextRegularS,
                textAlign = TextAlign.Center
            )

            VerticalSpacer(64.dp)

            OtpInput(
                value = state.inputOtp,
                otpLength = 6,
                onOtpChanged = { otp -> events(SignUpByOtpEvent.OnOtpProvided(otp)) }
            )

            if (state.isOtpValidErrorEnabled) {
                VerticalSpacer(16.dp)
                Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.validationError,
                        style = TextRegularS,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                )
            }

            VerticalSpacer(32.dp)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
                onClick = { events(SignUpByOtpEvent.OnConfirmButtonClicked) },
                enabled = state.isSignUpButtonStateEnabled
            ) {
                Text(
                    text = strings.submitButton,
                    style = TextBoldS,
                    color = PureWhite
                )
            }
        }
    }
}
