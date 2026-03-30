package pl.kosciukw.petsify.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import pl.kosciukw.petsify.feature.emails.EmailsListScreen
import pl.kosciukw.petsify.feature.profile.ProfileScreen

@Composable
fun MainScreen(
  onOpenEmailDetails: (emailId: Int) -> Unit,
  onComposeNewEmail: () -> Unit
) {
  var selectedTab by remember { mutableStateOf(MainTab.Emails) }
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      MainBottomBar(
        selectedTab = selectedTab,
        onNavigateToEmails = { selectedTab = MainTab.Emails },
        onNavigateToProfile = { selectedTab = MainTab.Profile }
      )
    }
  ) { paddingValues ->
    when (selectedTab) {
      MainTab.Emails -> EmailsListScreen(
        onOpenEmailDetails = onOpenEmailDetails,
        onComposeNewEmail = onComposeNewEmail,
        modifier = Modifier.padding(paddingValues)
      )

      MainTab.Profile -> ProfileScreen(
        modifier = Modifier.padding(paddingValues)
      )
    }
  }
}

@Composable
private fun MainBottomBar(
  selectedTab: MainTab,
  onNavigateToEmails: () -> Unit,
  onNavigateToProfile: () -> Unit
) {
  NavigationBar {
    NavigationBarItem(
      selected = selectedTab == MainTab.Emails,
      icon = { Icon(imageVector = Icons.Default.Email, "emails") },
      onClick = onNavigateToEmails
    )
    NavigationBarItem(
      selected = selectedTab == MainTab.Profile,
      icon = { Icon(imageVector = Icons.Default.Person, "profile") },
      onClick = onNavigateToProfile
    )
  }
}

private enum class MainTab {
  Emails,
  Profile
}
