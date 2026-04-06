package com.kosciukw.services.internal.user.api

import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel

interface UserApi {

    suspend fun loginByPassword(
        url: String,
        loginByPasswordRequest: LoginByPasswordRequest
    ): AccessTokenApiModel

    suspend fun startOtpRegistration(
        url: String,
        startOtpRegistrationRequest: StartOtpRegistrationRequest
    )

    suspend fun finalizeOtpRegistration(
        url: String,
        finalizeOtpRegistrationRequest: FinalizeOtpRegistrationRequest
    ): AccessTokenApiModel

    suspend fun refreshToken(
        url: String,
        refreshRequest: RefreshRequest
    ): AccessTokenApiModel
}
