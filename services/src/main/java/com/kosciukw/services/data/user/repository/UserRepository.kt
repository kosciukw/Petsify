package com.kosciukw.services.data.user.repository

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel

interface UserRepository {

    suspend fun pairDeviceByPassword(
        pairByPasswordDomainModel: PairByPasswordDomainModel
    ): AccessTokenApiModel

    suspend fun startOtpRegistration(
        startOtpRegistrationDomainModel: StartOtpRegistrationDomainModel
    )

    suspend fun signUp(
        signUpDomainModel: SignUpDomainModel
    )
}