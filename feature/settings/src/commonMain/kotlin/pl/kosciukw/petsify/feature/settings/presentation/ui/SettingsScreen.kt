package pl.kosciukw.petsify.feature.settings.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.settings.presentation.SettingsAction
import pl.kosciukw.petsify.feature.settings.presentation.SettingsEvent
import pl.kosciukw.petsify.feature.settings.presentation.SettingsState
import pl.kosciukw.petsify.shared.presentation.UIComponent
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.SettingsStrings
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.BodyM
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    state: SettingsState,
    errors: Flow<UIComponent>,
    events: (SettingsEvent) -> Unit,
    action: Flow<SettingsAction>,
    strings: SettingsStrings,
    commonStrings: CommonScreenStrings
) {
    LaunchedEffect(Unit) {
        events(SettingsEvent.OnStart)
    }

    LaunchedEffect(action) {
        action.collect {
            // no-op
        }
    }

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
        topBar = {
            TopAppBar(
                title = { Text(strings.title) }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingXXL)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(modifier = Modifier.size(1.dp, 96.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = strings.body,
                    style = TextRegularS
                )
            }

            if (state.progressBarState != ProgressBarState.Idle) {
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
