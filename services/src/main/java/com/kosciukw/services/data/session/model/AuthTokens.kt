package com.kosciukw.services.data.session.model

import pl.kosciukw.petsify.shared.utils.empty

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
) {
    override fun toString() = String.empty()
}
