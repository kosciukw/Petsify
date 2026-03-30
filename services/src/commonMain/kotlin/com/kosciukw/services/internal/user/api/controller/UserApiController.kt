package com.kosciukw.services.internal.user.api.controller

import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel

interface UserApiController {

    suspend fun loginByPassword(request: LoginByPasswordRequest): AccessTokenApiModel

    suspend fun startOtpRegistrationRequest(request: StartOtpRegistrationRequest)

    suspend fun finalizeOtpRegistrationRequest(request: FinalizeOtpRegistrationRequest): AccessTokenApiModel

    suspend fun refreshToken(request: RefreshRequest): AccessTokenApiModel
}
