package com.kosciukw.services.api.session.model

import pl.kosciukw.petsify.shared.utils.empty

data class RefreshTokenDomainModel(
    val refreshToken: String
) {
    override fun toString() = String.empty()
}
