package pl.kosciukw.petsify.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
data class SignUpByOtpNavArgs(
    val email: String,
    val password: String,
    val name: String,
    val termsAccepted: Boolean,
    val marketingAccepted: Boolean
) : TypedNavArgs
