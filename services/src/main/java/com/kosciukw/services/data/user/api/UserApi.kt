package com.kosciukw.services.data.user.api

import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import retrofit2.http.Body
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
    suspend fun signUp(
        @Url url: String,
        @Body signUpRequest: SignUpRequest
    )
}