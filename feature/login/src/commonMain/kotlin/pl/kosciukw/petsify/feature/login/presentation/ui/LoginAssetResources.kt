package pl.kosciukw.petsify.feature.login.presentation.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import petsify.feature.login.generated.resources.Res
import petsify.feature.login.generated.resources.ic_bone
import petsify.feature.login.generated.resources.ic_google
import petsify.feature.login.generated.resources.ic_paw
import petsify.feature.login.generated.resources.login_screen_background
import petsify.feature.login.generated.resources.login_screen_header_image

@Composable
fun rememberLoginScreenAssets(): LoginScreenAssets = LoginScreenAssets(
    pawPainter = painterResource(Res.drawable.ic_paw),
    bonePainter = painterResource(Res.drawable.ic_bone),
    googlePainter = painterResource(Res.drawable.ic_google),
    headerBackgroundPainter = painterResource(Res.drawable.login_screen_background),
    headerPainter = painterResource(Res.drawable.login_screen_header_image)
)
