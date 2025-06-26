package com.kosciukw.services.data.user.service.user

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel

interface UserService {

    suspend fun loginDeviceByPassword(
        request: LoginByPasswordDomainModel
    ): AccessTokenApiModel

    suspend fun startOtpRegistration(
        request: StartOtpRegistrationDomainModel
    )

    suspend fun signUp(
        request: SignUpDomainModel
    )
}