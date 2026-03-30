package pl.kosciukw.petsify.feature.signup.presentation.ui

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.signup.presentation.SignUpAction
import pl.kosciukw.petsify.feature.signup.presentation.SignUpEvent
import pl.kosciukw.petsify.feature.signup.presentation.SignUpState
import pl.kosciukw.petsify.shared.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.SignUpStrings
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.BodyM
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.TextS
import pl.kosciukw.petsify.shared.ui.theme.TextSecondary
import pl.kosciukw.petsify.shared.ui.theme.paddingM
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
        ) {
            VerticalSpacer(96.dp)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = strings.intro,
                style = TextRegularS,
                textAlign = TextAlign.Center
            )

            VerticalSpacer(32.dp)

            PetsifyTextField(
                value = state.inputName,
                onValueChange = { events(SignUpEvent.OnNameTextChanged(it)) },
                label = strings.nameLabel,
                errorMessage = strings.nameError,
                isError = state.isNameValidationErrorEnabled
            )

            VerticalSpacer(12.dp)

            PetsifyTextField(
                value = state.inputEmail,
                onValueChange = { events(SignUpEvent.OnEmailTextChanged(it)) },
                label = strings.emailLabel,
                errorMessage = strings.emailError,
                isError = state.isEmailValidationErrorEnabled,
                keyboardType = KeyboardType.Email
            )

            VerticalSpacer(12.dp)

            PetsifyTextField(
                value = state.inputPassword,
                onValueChange = { events(SignUpEvent.OnPasswordChanged(it)) },
                label = strings.passwordLabel,
                errorMessage = strings.passwordError,
                isError = state.isPasswordValidationErrorEnabled,
                keyboardType = KeyboardType.Password
            )

            VerticalSpacer(12.dp)

            PetsifyTextField(
                value = state.inputConfirmPassword,
                onValueChange = { events(SignUpEvent.OnConfirmPasswordChanged(it)) },
                label = strings.confirmPasswordLabel,
                errorMessage = strings.confirmPasswordError,
                isError = state.isConfirmPasswordValidationErrorEnabled,
                keyboardType = KeyboardType.Password
            )

            VerticalSpacer(16.dp)

            PetsifyCheckboxRow(
                checked = state.isTermsAccepted,
                text = strings.termsConsent,
                onCheckedChange = { events(SignUpEvent.OnTermsAcceptedChanged(it)) }
            )

            VerticalSpacer(16.dp)

            PetsifyCheckboxRow(
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

@Composable
internal fun VerticalSpacer(height: Dp) {
    Box(modifier = Modifier.size(1.dp, height))
}

@Composable
private fun PetsifyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        label = {
            Text(
                text = label,
                style = TextSecondary.copy(
                    fontSize = TextS,
                    textAlign = TextAlign.Start
                )
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextRegularS.copy(textAlign = TextAlign.Start),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = TextS
                    )
                )
            }
        },
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            errorTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            errorLabelColor = MaterialTheme.colorScheme.error,
            cursorColor = MaterialTheme.colorScheme.onBackground,
            errorCursorColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun PetsifyCheckboxRow(
    checked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onCheckedChange(!checked) }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(!checked) },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondaryContainer,
                checkmarkColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
        Text(
            text = text,
            style = TextRegularS.copy(textAlign = TextAlign.Start)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PetsifyScreenScaffold(
    title: String,
    onNavigateUp: (() -> Unit)?,
    errors: Flow<UIComponent>,
    progressBarState: ProgressBarState,
    commonStrings: CommonScreenStrings,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val errorQueue = remember { mutableStateListOf<UIComponent>() }

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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = { Text(title) },
                navigationIcon = {
                    if (onNavigateUp != null) {
                        IconButton(onClick = onNavigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = commonStrings.navigateUpContentDescription
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()

            if (progressBarState != ProgressBarState.Idle) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlackLiquorice.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = GoshawkGrey)
                }
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
