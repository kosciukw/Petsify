package pl.kosciukw.petsify.feature.emaildetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.kosciukw.petsify.shared.navigation.AppRoute

fun NavGraphBuilder.emailDetailsScreen(
  onNavigateUp: () -> Unit,
  onReplyToEmail: () -> Unit
) {
  composable<AppRoute.EmailDetails> {
    EmailDetailsScreen(
      onReplyToEmail = onReplyToEmail,
      onNavigateUp = onNavigateUp
    )
  }
}

fun NavController.navigateToEmailDetails(emailId: Int) {
  navigate(AppRoute.EmailDetails(emailId = emailId))
}
