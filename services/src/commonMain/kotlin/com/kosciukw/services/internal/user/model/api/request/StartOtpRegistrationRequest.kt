package com.kosciukw.services.internal.user.model.api.request

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.utils.empty

@Serializable
data class StartOtpRegistrationRequest(
    val email: String
) {
    override fun toString() = String.empty()
}
