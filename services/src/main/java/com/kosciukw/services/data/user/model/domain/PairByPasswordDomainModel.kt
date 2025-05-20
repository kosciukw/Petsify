package com.kosciukw.services.data.user.model.domain

import pl.kosciukw.petsify.shared.utils.empty

data class PairByPasswordDomainModel(
    val email: String,
    val password: String
) {
    override fun toString() = String.empty()
}