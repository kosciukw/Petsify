package pl.kosciukw.petsify.feature.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.home.presentation.HomeAction
import pl.kosciukw.petsify.feature.home.presentation.HomeEvent
import pl.kosciukw.petsify.feature.home.presentation.HomeState
import pl.kosciukw.petsify.shared.ui.R
import pl.kosciukw.petsify.shared.ui.UIComponent
import pl.kosciukw.petsify.shared.ui.components.base.BaseScreen
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer96dp
import pl.kosciukw.petsify.shared.ui.components.toolbar.ToolbarConfig
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@Composable
internal fun HomeScreen(
    state: HomeState,
    errors: Flow<UIComponent>,
    events: (HomeEvent) -> Unit,
    action: Flow<HomeAction>
) {
    LaunchedEffect(Unit) {
        events(HomeEvent.OnStart)
    }
    LaunchedEffect(action) {
        action.collect {
            // no-op
        }
    }

    BaseScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        errors = errors,
        progressBarState = state.progressBarState,
        toolbarConfig = ToolbarConfig(titleToolbar = stringResource(R.string.home_screen_header)),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingXXL)
            ) {
                Spacer96dp()

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.home_screen_title),
                    style = TextRegularS,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}
