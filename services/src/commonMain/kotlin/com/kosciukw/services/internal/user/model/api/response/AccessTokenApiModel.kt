package com.kosciukw.services.internal.user.model.api.response

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.utils.empty

@Serializable
data class AccessTokenApiModel(
    val accessToken: String,
    val refreshToken: String?
) {
    override fun toString() = String.empty()
}
