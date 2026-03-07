package pl.kosciukw.petsify

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.kosciukw.petsify.feature.composer.composerScreenRoot
import pl.kosciukw.petsify.feature.composer.navigateToComposer
import pl.kosciukw.petsify.feature.emaildetails.emailDetailsScreen
import pl.kosciukw.petsify.feature.emaildetails.navigateToEmailDetails
import pl.kosciukw.petsify.feature.login.navigation.loginScreen
import pl.kosciukw.petsify.feature.main.mainScreen
import pl.kosciukw.petsify.feature.main.navigateToMain
import pl.kosciukw.petsify.feature.login.navigation.navigateToLogin
import pl.kosciukw.petsify.feature.otp.navigation.navigateToSignUpByOtp
import pl.kosciukw.petsify.feature.otp.navigation.signUpByOtpScreen
import pl.kosciukw.petsify.feature.settings.navigation.settingsScreen
import pl.kosciukw.petsify.feature.signup.navigation.navigateToSignUp
import pl.kosciukw.petsify.feature.signup.navigation.signUpScreen
import pl.kosciukw.petsify.feature.splash.navigation.SplashDestination
import pl.kosciukw.petsify.feature.splash.navigation.splashScreen

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashDestination
    ) {

        splashScreen(
            onNavigateToMain = { navController.navigateToMain() },
            onNavigateToLogin = { navController.navigateToLogin() }
        )

        loginScreen(
            onNavigateToMain = { navController.navigateToMain() },
            onNavigateToSignUp = { navController.navigateToSignUp() }
        )

        signUpScreen(
            onNavigateToOtp = { navArgs -> navController.navigateToSignUpByOtp(navArgs) },
            onNavigateUp = { navController.navigateUp() },
            onNavigateToLogin = { navController.navigateToLogin() }
        )

        signUpByOtpScreen(
            onNavigateToMain = { navController.navigateToMain() },
            onNavigateUp = { navController.navigateUp() }
        )

        settingsScreen()

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
