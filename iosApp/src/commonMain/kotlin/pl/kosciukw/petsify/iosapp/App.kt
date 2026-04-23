package pl.kosciukw.petsify.iosapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pl.kosciukw.petsify.feature.composer.navigation.IosComposerRoute
import pl.kosciukw.petsify.feature.emaildetails.navigation.IosEmailDetailsRoute
import pl.kosciukw.petsify.feature.login.navigation.IosLoginRoute
import pl.kosciukw.petsify.feature.main.navigation.IosAddPetRoute
import pl.kosciukw.petsify.feature.main.navigation.IosMainRoute
import pl.kosciukw.petsify.feature.otp.navigation.IosSignUpOtpRoute
import pl.kosciukw.petsify.feature.signup.navigation.IosSignUpRoute
import pl.kosciukw.petsify.feature.splash.navigation.IosSplashRoute
import pl.kosciukw.petsify.shared.navigation.AppRoute
import pl.kosciukw.petsify.shared.ui.theme.PetsifyTheme

@Composable
fun IosApp() {
    remember { initIosKoin() }
    val routeController = remember { IosRouteController() }
    val currentRoute = routeController.currentRoute

    PetsifyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            when (currentRoute) {
                AppRoute.Splash -> IosSplashRoute(
                    onNavigateToLogin = { routeController.navigateTo(AppRoute.Login) },
                    onNavigateToMain = { routeController.navigateTo(AppRoute.Main) }
                )

                AppRoute.Login -> IosLoginRoute(
                    onNavigateToMain = { routeController.navigateTo(AppRoute.Main) },
                    onNavigateToSignUp = { routeController.navigateTo(AppRoute.SignUp) }
                )

                AppRoute.SignUp -> IosSignUpRoute(
                    onNavigateToLogin = { routeController.navigateTo(AppRoute.Login) },
                    onNavigateUp = { routeController.navigateTo(AppRoute.Login) },
                    onNavigateToOtp = { navArgs ->
                        routeController.navigateTo(AppRoute.SignUpOtp(navArgs))
                    }
                )

                is AppRoute.SignUpOtp -> IosSignUpOtpRoute(
                    navArgs = currentRoute.navArgs,
                    onNavigateToMain = { routeController.navigateTo(AppRoute.Main) },
                    onNavigateUp = { routeController.navigateTo(AppRoute.SignUp) }
                )

                AppRoute.Main -> IosMainRoute(
                    onNavigateToAddPet = { routeController.navigateTo(AppRoute.AddPet) }
                )

                AppRoute.AddPet -> IosAddPetRoute(
                    onNavigateUp = { routeController.navigateTo(AppRoute.Main) }
                )

                is AppRoute.EmailDetails -> IosEmailDetailsRoute(
                    onNavigateUp = { routeController.navigateTo(AppRoute.Main) },
                    onReplyToEmail = { routeController.navigateTo(AppRoute.Composer) }
                )

                AppRoute.Composer -> IosComposerRoute(
                    onNavigateUp = { routeController.navigateTo(AppRoute.Main) }
                )

                AppRoute.Settings -> Unit
            }
        }
    }
}
