package com.kosciukw.services.data.user.api.controller

import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel

interface UserApiController {

    suspend fun pairByPassword(request: PairByPasswordRequest): AccessTokenApiModel

    suspend fun startOtpRegistrationRequest(request: StartOtpRegistrationRequest)

    suspend fun signUp(request: SignUpRequest)
}