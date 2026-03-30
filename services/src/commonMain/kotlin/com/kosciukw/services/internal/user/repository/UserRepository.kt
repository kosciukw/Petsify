package com.kosciukw.services.internal.user.repository

import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel
import com.kosciukw.services.api.session.model.RefreshTokenDomainModel
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel

interface UserRepository {

    suspend fun loginDeviceByPassword(
        loginByPasswordDomainModel: LoginByPasswordDomainModel
    ): AccessTokenApiModel

    suspend fun startOtpRegistration(
        startOtpRegistrationDomainModel: StartOtpRegistrationDomainModel
    )

    suspend fun finalizeOtpRegistration(
        finalizeOtpRegistrationDomainModel: FinalizeOtpRegistrationDomainModel
    ): AccessTokenApiModel

    suspend fun refreshToken(
        refreshTokenDomainModel: RefreshTokenDomainModel
    ): AccessTokenApiModel
}
