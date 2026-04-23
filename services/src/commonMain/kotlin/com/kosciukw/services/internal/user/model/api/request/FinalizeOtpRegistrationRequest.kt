package com.kosciukw.services.internal.user.model.api.request

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.utils.empty

@Serializable
data class FinalizeOtpRegistrationRequest(
    val email: String,
    val password: String,
    val name: String,
    val termsAccepted: Boolean,
    val marketingAccepted: Boolean,
    val otp: String
) {
    override fun toString() = String.empty()
}
