package com.kosciukw.services.data.user.repository

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel

interface UserRepository {

    suspend fun pairDeviceByPassword(
        pairByPasswordDomainModel: PairByPasswordDomainModel
    ): AccessTokenApiModel

    suspend fun signUp(
        signUpDomainModel: SignUpDomainModel
    )
}