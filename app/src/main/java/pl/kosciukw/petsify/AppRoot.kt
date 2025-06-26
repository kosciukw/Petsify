package pl.kosciukw.petsify

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.kosciukw.petsify.feature.composer.composerScreenRoot
import pl.kosciukw.petsify.feature.composer.navigateToComposer
import pl.kosciukw.petsify.feature.emaildetails.emailDetailsScreen
import pl.kosciukw.petsify.feature.emaildetails.navigateToEmailDetails
import pl.kosciukw.petsify.feature.login.navigation.LoginDestination
import pl.kosciukw.petsify.feature.login.navigation.loginScreen
import pl.kosciukw.petsify.feature.main.mainScreen
import pl.kosciukw.petsify.feature.main.navigateToMain
import pl.kosciukw.petsify.feature.login.navigation.navigateToLogin
import pl.kosciukw.petsify.feature.signup.navigation.navigateToSignUp
import pl.kosciukw.petsify.feature.signup.navigation.signUpScreen

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
            onNavigateToOtp = {
               //  todo finalize otp registration
            },
            onNavigateUp = { navController.navigateUp() },
            onNavigateToLogin = { navController.navigateToLogin() }
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