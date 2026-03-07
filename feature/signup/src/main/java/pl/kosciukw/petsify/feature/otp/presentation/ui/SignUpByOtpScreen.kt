package pl.kosciukw.petsify.feature.otp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import pl.kosciukw.petsify.feature.components.OtpInput
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpState
import pl.kosciukw.petsify.shared.ui.R
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.base.BaseScreen
import pl.kosciukw.petsify.shared.ui.components.button.ButtonRegular
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer16dp
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer32dp
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer64dp
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer96dp
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@Composable
internal fun SignUpByOtpScreen(
    state: SignUpByOtpState,
    onNavigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    errors: Flow<UIComponent>,
    events: (SignUpByOtpEvent) -> Unit,
    action: Flow<SignUpByOtpAction>
) {
    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(Unit) {
        currentAction.collectLatest { act ->
            when (act) {
                is SignUpByOtpAction.Navigation.NavigateToHome -> onNavigateToHome()
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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingXXL)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer96dp()

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.sign_up_by_otp_screen),
                    style = TextRegularS,
                    textAlign = TextAlign.Center
                )

                Spacer64dp()

                OtpInput(
                    value = state.inputOtp,
                    otpLength = 6,
                    onOtpChanged = { otp -> events(SignUpByOtpEvent.OnOtpProvided(otp)) }
                )

                if (state.isOtpValidErrorEnabled) {
                    Spacer16dp()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.otp_validation_error),
                        style = TextRegularS,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer32dp()

                ButtonRegular(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    style = TextBoldS,
                    buttonColors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
                    onClick = { events(SignUpByOtpEvent.OnConfirmButtonClicked) },
                    label = stringResource(id = R.string.sign_up_screen_sign_up),
                    textColor = PureWhite,
                    isButtonEnabled = state.isSignUpButtonStateEnabled
                )
            }
        }
    )
}