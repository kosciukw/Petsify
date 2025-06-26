package com.kosciukw.services.data.user.model.domain

import pl.kosciukw.petsify.shared.utils.empty

data class SignUpDomainModel(
    val email: String,
    val password: String,
    val name: String,
    val termsAccepted: Boolean,
    val marketingAccepted: Boolean
) {
    override fun toString() = String.empty()
}