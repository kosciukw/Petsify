package pl.kosciukw.petsify.feature.emails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme
import pl.kosciukw.petsify.shared.utils.empty

//TODO 10.05.2025: To be deleted
@Composable
internal fun EmailsListScreen(
  onOpenEmailDetails: (id: Int) -> Unit,
  onComposeNewEmail: () -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets(0.dp),
    topBar = { EmailsSearchBar() },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = onComposeNewEmail,
        text = { Text(text = "Compose") },
        icon = { Icon(imageVector = Icons.Default.Add, null) }
      )
    }
  ) { paddingValues ->
    val emails = (1..20).map { "Email Item $it" }
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      itemsIndexed(emails) { index, email ->
        ListItem(
          modifier = Modifier.clickable { onOpenEmailDetails(index) },
          headlineContent = {
            Text(text = email)
          }
        )
      }
    }
  }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EmailsSearchBar() {
  SearchBar(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp),
    expanded = false,
    windowInsets = WindowInsets(0.dp),
    inputField = {
      SearchBarDefaults.InputField(
        query = String.empty(),
        onQueryChange = {},
        onSearch = {},
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, null)
        },
        placeholder = {
          Text(text = "Search Email")
        },
        expanded = false,
        onExpandedChange = {}
      )
    },
    onExpandedChange = {},
    content = {}
  )
}

@Preview
@Composable
private fun PreviewEmailsList() {
  PetsifyTheme {
    EmailsListScreen(
      onOpenEmailDetails = {},
      onComposeNewEmail = {}
    )
  }
}