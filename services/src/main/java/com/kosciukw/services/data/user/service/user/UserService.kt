package com.kosciukw.services.data.user.service.user

import com.kosciukw.services.data.user.model.domain.AuthSessionDomainModel
import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.RefreshTokenDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel

interface UserService {

    suspend fun loginDeviceByPassword(
        request: LoginByPasswordDomainModel
    ): AuthSessionDomainModel

    suspend fun startOtpRegistration(
        request: StartOtpRegistrationDomainModel
    )

    suspend fun finalizeOtpRegistration(
        request: FinalizeOtpRegistrationDomainModel
    ): AuthSessionDomainModel

    suspend fun refreshToken(
        request: RefreshTokenDomainModel
    ): AuthSessionDomainModel

    suspend fun isSignedIn(): Boolean
}
