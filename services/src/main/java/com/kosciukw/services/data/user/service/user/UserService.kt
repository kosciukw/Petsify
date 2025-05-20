package com.kosciukw.services.data.user.service.user

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel

interface UserService {

    suspend fun pairDeviceByPassword(
        request: PairByPasswordDomainModel
    ): AccessTokenApiModel
}