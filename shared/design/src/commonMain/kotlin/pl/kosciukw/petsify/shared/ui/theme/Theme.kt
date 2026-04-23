package pl.kosciukw.petsify.shared.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = GoshawkGrey,
  secondary = Argent,
  tertiary = BlackLiquorice,
  background = PureWhite,
  onBackground = GoshawkGrey,
  surface = CerebralGrey,
  onSurface = GoshawkGrey,
  surfaceContainer = CleanCanvas,
  secondaryContainer = BlackLiquorice,
  onSecondaryContainer = PureWhite,
  tertiaryContainer = Mercury,
  onTertiaryContainer = GoshawkGrey,
  error = CoralRed,
  errorContainer = CoralRed,
  outline = BlackLiquorice
)

private val LightColorScheme = DarkColorScheme

@Composable
fun PetsifyTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
