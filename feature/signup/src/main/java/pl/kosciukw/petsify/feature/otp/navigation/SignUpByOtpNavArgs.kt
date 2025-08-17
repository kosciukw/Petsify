package pl.kosciukw.petsify.feature.otp.navigation

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.navigation.TypedNavArgs


@Serializable
data class SignUpByOtpNavArgs(
    val email: String,
    val password: String,
    val name: String,
    val termsAccepted: Boolean,
    val marketingAccepted: Boolean
) : TypedNavArgs

