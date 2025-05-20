package pl.kosciukw.petsify.shared.ui.components.toast

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MakeToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    val context = LocalContext.current
    Toast.makeText(context, message, duration).show()
}