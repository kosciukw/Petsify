package com.kosciukw.services.internal.user.model.api.request

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.utils.empty

@Serializable
data class LoginByPasswordRequest(
    val email: String,
    val password: String
) {
    override fun toString() = String.empty()
}
