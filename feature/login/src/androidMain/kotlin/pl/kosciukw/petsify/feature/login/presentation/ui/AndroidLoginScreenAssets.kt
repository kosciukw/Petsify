package pl.kosciukw.petsify.feature.login.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import pl.kosciukw.petsify.feature.login.R

@Composable
internal fun rememberAndroidLoginScreenAssets(): LoginScreenAssets = LoginScreenAssets(
    pawPainter = painterResource(R.drawable.ic_paw),
    bonePainter = painterResource(R.drawable.ic_bone),
    googlePainter = painterResource(R.drawable.ic_google),
    headerBackgroundPainter = painterResource(R.drawable.login_screen_background),
    headerPainter = painterResource(R.drawable.login_screen_header_image)
)
