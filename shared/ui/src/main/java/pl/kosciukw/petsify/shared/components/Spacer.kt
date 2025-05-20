package pl.kosciukw.petsify.shared.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spacer8dp() {
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun Spacer32dp() {
    Spacer(modifier = Modifier.size(32.dp))
}