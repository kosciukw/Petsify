package com.kosciukw.services.api.registration.model

import pl.kosciukw.petsify.shared.utils.empty

data class StartOtpRegistrationDomainModel(
   val email: String
) {
    override fun toString() = String.empty()
}
