package pl.kosciukw.petsify.shared.ui.components.base

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.dialog.CreateUIComponentDialog
import pl.kosciukw.petsify.shared.ui.components.progress.LoadingScreen
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.snackbar.AppSnackbar
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarCustom

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    errors: Flow<UIComponent>,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    networkState: NetworkState = NetworkState.Established,
    content: @Composable () -> Unit,
    toolbarConfig: ToolbarConfig? = null,
    onTryAgain: () -> Unit = {}
) {
    val errorQueue = remember { mutableStateListOf<UIComponent>() }

    LaunchedEffect(errors) {
        errors.collect { error ->
            errorQueue.add(error)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            toolbarConfig?.let { config -> ToolbarCustom(toolbarConfig = config) }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()

            val isSnackbarVisible = remember { mutableStateOf(false) }
            if (errorQueue.isNotEmpty()) {
                when (val uiComponent = errorQueue.first()) {
                    is UIComponent.Dialog -> {
                        CreateUIComponentDialog(
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = {
                                errorQueue.removeAt(0)
                            }
                        )
                    }

                    is UIComponent.ToastSimple -> {
                        isSnackbarVisible.value = true
                        AppSnackbar(
                            title = uiComponent.title,
                            isVisible = isSnackbarVisible.value,
                            onDismiss = {
                                isSnackbarVisible.value = false
                                errorQueue.removeAt(0)
                            },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }

                    is UIComponent.None -> {
                        // no-op
                    }
                }
            }
        }
    }

    if (networkState == NetworkState.Disconnected && progressBarState == ProgressBarState.Idle) {
        // FailedNetworkScreen(onTryAgain = onTryAgain)
    }

    if (progressBarState is ProgressBarState.LoadingWithLogo) {
        // LoadingWithLogoScreen()
    }

    if (progressBarState is ProgressBarState.ScreenLoading || progressBarState is ProgressBarState.FullScreenLoading) {
        LoadingScreen()
    }
}