package com.kosciukw.services.data.user.repository

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel

interface UserRepository {

    suspend fun pairDeviceByPassword(
        pairByPasswordDomainModel: PairByPasswordDomainModel
    ): AccessTokenApiModel
}