package com.kosciukw.services.api.registration.model

import pl.kosciukw.petsify.shared.utils.empty

data class FinalizeOtpRegistrationDomainModel(
    val email: String,
    val password: String,
    val name: String,
    val termsAccepted: Boolean,
    val marketingAccepted: Boolean,
    val otp: String
) {
    override fun toString() = String.empty()
}
