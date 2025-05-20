package pl.kosciukw.petsify.feature.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme
import pl.kosciukw.petsify.shared.ui.R as SharedR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    onNavigateToMain: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = SharedR.string.navigate_up_content_description)
                        )
                    }
                },
                title = { Text(text = stringResource(id = SharedR.string.sign_up_screen_header)) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onNavigateToMain) {
                Text(text = stringResource(id = SharedR.string.sign_up_screen_header))
            }
        }
    }
}


@Preview
@Composable
private fun PreviewSignUpScreen() {
    PetsifyTheme {
        SignUpScreen(
            onNavigateToMain = {},
            onNavigateUp = {}
        )
    }
}