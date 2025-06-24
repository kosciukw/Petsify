package com.kosciukw.services.data.user.model.domain

import pl.kosciukw.petsify.shared.utils.empty

data class StartOtpRegistrationDomainModel(
   val email: String
) {
    override fun toString() = String.empty()
}