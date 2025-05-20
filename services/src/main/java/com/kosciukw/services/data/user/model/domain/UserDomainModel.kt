package com.kosciukw.services.data.user.model.domain

import pl.kosciukw.petsify.shared.utils.empty

data class UserDomainModel(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val profilePictureUrl: String? = null
) {
    override fun toString() = String.empty()
}