package pl.kosciukw.petsify.iosapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.presentation.common.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.presentation.components.view.ViewEvent
import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme

@Composable
fun IosApp() {
    val viewModel = remember { IosHomeViewModel() }
    val state by viewModel.state.collectAsState()

    DisposableEffect(viewModel) {
        onDispose { viewModel.clear() }
    }

    PetsifyTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.title,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = state.message,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )
                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { viewModel.setEvent(IosHomeEvent.ToggleDetails) }
                ) {
                    Text(state.buttonLabel)
                }
            }
        }
    }
}

private class IosHomeViewModel : BaseViewModel<IosHomeEvent, IosHomeState, IosHomeAction>(
    integrationErrorMapper = NoOpIntegrationErrorMapper
) {
    override fun setInitialState() = IosHomeState()

    override fun onTriggerEvent(event: IosHomeEvent) {
        when (event) {
            IosHomeEvent.ToggleDetails -> {
                setState {
                    copy(
                        message = if (detailsVisible) {
                            "Framework iOS jest gotowy do podpięcia pod natywny host."
                        } else {
                            "Wspólny Compose screen i shared ViewModel działają już po stronie iOS."
                        },
                        buttonLabel = if (detailsVisible) "Pokaż wspólny ekran" else "Pokaż status frameworku",
                        detailsVisible = !detailsVisible
                    )
                }
            }
        }
    }
}

private data class IosHomeState(
    val title: String = "Petsify iOS",
    val message: String = "Wspólny Compose screen i shared ViewModel działają już po stronie iOS.",
    val buttonLabel: String = "Pokaż status frameworku",
    val detailsVisible: Boolean = false
) : ViewState

private sealed interface IosHomeEvent : ViewEvent {
    data object ToggleDetails : IosHomeEvent
}

private sealed interface IosHomeAction : ViewSingleAction

private object NoOpIntegrationErrorMapper : IntegrationErrorMapper {
    override fun map(error: Throwable): AppError {
        return AppError.TechnicalError.Unknown(error.message ?: "Unknown iOS error")
    }
}
