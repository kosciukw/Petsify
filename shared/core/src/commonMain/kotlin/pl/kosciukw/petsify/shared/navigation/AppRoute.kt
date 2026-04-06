package pl.kosciukw.petsify.shared.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object SignUp : AppRoute

    @Serializable
    data class SignUpOtp(val navArgs: SignUpByOtpNavArgs) : AppRoute

    @Serializable
    data object Main : AppRoute

    @Serializable
    data object AddPet : AppRoute

    @Serializable
    data class EmailDetails(val emailId: Int) : AppRoute

    @Serializable
    data object Composer : AppRoute

    @Serializable
    data object Settings : AppRoute
}
