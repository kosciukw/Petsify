package com.kosciukw.services.api.registration

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel

interface RegistrationService {

    suspend fun startOtpRegistration(
        request: StartOtpRegistrationDomainModel
    )

    suspend fun finalizeOtpRegistration(
        request: FinalizeOtpRegistrationDomainModel
    ): AuthSessionDomainModel
}
