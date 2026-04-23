package pl.kosciukw.petsify.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
  onNavigateToAddPet: () -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize()
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = "Welcome to Petsify",
        style = MaterialTheme.typography.headlineMedium
      )
      Text(
        text = "Start by adding your first pet.",
        modifier = Modifier.padding(top = 12.dp, bottom = 24.dp),
        style = MaterialTheme.typography.bodyLarge
      )
      Button(
        onClick = onNavigateToAddPet
      ) {
        Text(text = "Add Pet")
      }
    }
  }
}
