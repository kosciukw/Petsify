package pl.kosciukw.petsify

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.kosciukw.petsify.feature.composer.composerScreenRoot
import pl.kosciukw.petsify.feature.composer.navigateToComposer
import pl.kosciukw.petsify.feature.emaildetails.emailDetailsScreen
import pl.kosciukw.petsify.feature.emaildetails.navigateToEmailDetails
import pl.kosciukw.petsify.feature.pairdevice.navigation.LoginDestination
import pl.kosciukw.petsify.feature.pairdevice.navigation.loginScreen
import pl.kosciukw.petsify.feature.main.mainScreen
import pl.kosciukw.petsify.feature.main.navigateToMain
import pl.kosciukw.petsify.feature.signup.navigateToSignUp
import pl.kosciukw.petsify.feature.signup.signUpScreen

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {

        loginScreen(
            onNavigateToMain = { navController.navigateToMain() },
            onNavigateToSignUp = { navController.navigateToSignUp() }
        )

        signUpScreen(
            onNavigateToMain = { navController.navigateToMain() },
            onNavigateUp = { navController.navigateUp() }
        )

        mainScreen(
            onOpenEmailDetails = { emailId -> navController.navigateToEmailDetails(emailId) },
            onComposeNewEmail = { navController.navigateToComposer() }
        )

        emailDetailsScreen(
            onReplyToEmail = { navController.navigateToComposer() },
            onNavigateUp = { navController.navigateUp() }
        )

    composerScreenRoot(
      onNavigateUp = { navController.navigateUp() }
    )
  }
}