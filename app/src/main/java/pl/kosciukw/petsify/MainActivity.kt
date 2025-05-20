package pl.kosciukw.petsify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PetsifyTheme {
        AppRoot()
      }
    }
  }
}