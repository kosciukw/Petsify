package com.kosciukw.services.data.user.api.controller

import com.kosciukw.services.data.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel

interface UserApiController {

    suspend fun loginByPassword(request: LoginByPasswordRequest): AccessTokenApiModel

    suspend fun startOtpRegistrationRequest(request: StartOtpRegistrationRequest)

    suspend fun finalizeOtpRegistrationRequest(request: FinalizeOtpRegistrationRequest): AccessTokenApiModel

    @Deprecated("Use Start + FinalizeRegistration")
    suspend fun signUp(request: SignUpRequest)
}