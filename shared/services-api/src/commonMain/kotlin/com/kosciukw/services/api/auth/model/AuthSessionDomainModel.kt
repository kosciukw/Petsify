package com.kosciukw.services.api.auth.model

import pl.kosciukw.petsify.shared.utils.empty

data class AuthSessionDomainModel(
    val accessToken: String,
    val refreshToken: String
) {
    override fun toString() = String.empty()
}
