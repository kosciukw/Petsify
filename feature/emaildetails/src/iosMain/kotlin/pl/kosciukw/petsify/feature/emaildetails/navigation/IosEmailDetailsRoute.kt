package pl.kosciukw.petsify.feature.emaildetails.navigation

import androidx.compose.runtime.Composable
import pl.kosciukw.petsify.feature.emaildetails.EmailDetailsScreen

@Composable
fun IosEmailDetailsRoute(
    onNavigateUp: () -> Unit,
    onReplyToEmail: () -> Unit
) {
    EmailDetailsScreen(
        onNavigateUp = onNavigateUp,
        onReplyToEmail = onReplyToEmail
    )
}
