package com.kosciukw.services.data.user.api.controller

import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel

interface UserApiController {

    suspend fun pairByPassword(
        request: PairByPasswordRequest
    ): AccessTokenApiModel
}