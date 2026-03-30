package com.kosciukw.services.api.auth.model

import pl.kosciukw.petsify.shared.utils.empty

data class LoginByPasswordDomainModel(
    val email: String,
    val password: String
) {
    override fun toString() = String.empty()
}
