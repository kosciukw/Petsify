package com.kosciukw.services.data.user.api

import com.kosciukw.services.data.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.api.request.RefreshRequest
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface UserApi {

    @POST
    suspend fun loginByPassword(
        @Url url: String,
        @Body loginByPasswordRequest: LoginByPasswordRequest
    ): AccessTokenApiModel

    @POST
    suspend fun startOtpRegistration(
        @Url url: String,
        @Body startOtpRegistrationRequest: StartOtpRegistrationRequest
    )

    @POST
    suspend fun finalizeOtpRegistration(
        @Url url: String,
        @Body finalizeOtpRegistrationRequest: FinalizeOtpRegistrationRequest
    ): AccessTokenApiModel

    @POST
    @Headers("X-Bypass-Auth: true")
    suspend fun refreshToken(
        @Url url: String,
        @Body refreshRequest: RefreshRequest
    ) : AccessTokenApiModel
}
