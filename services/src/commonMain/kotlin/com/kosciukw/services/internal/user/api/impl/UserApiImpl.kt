package com.kosciukw.services.internal.user.api.impl

import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserApiImpl(
    private val httpClient: HttpClient
) : UserApi {

    override suspend fun loginByPassword(
        url: String,
        loginByPasswordRequest: LoginByPasswordRequest
    ): AccessTokenApiModel = execute(url, loginByPasswordRequest)

    override suspend fun startOtpRegistration(
        url: String,
        startOtpRegistrationRequest: StartOtpRegistrationRequest
    ) {
        execute<Unit>(url, startOtpRegistrationRequest)
    }

    override suspend fun finalizeOtpRegistration(
        url: String,
        finalizeOtpRegistrationRequest: FinalizeOtpRegistrationRequest
    ): AccessTokenApiModel = execute(url, finalizeOtpRegistrationRequest)

    override suspend fun refreshToken(
        url: String,
        refreshRequest: RefreshRequest
    ): AccessTokenApiModel = execute(
        endpoint = url,
        body = refreshRequest,
        bypassAuth = true
    )

    private suspend inline fun <reified T> execute(
        endpoint: String,
        body: Any,
        bypassAuth: Boolean = false
    ): T = httpClient.post(endpoint) {
        setBody(body)
        if (bypassAuth) {
            headers.append("X-Bypass-Auth", "true")
        }
    }.body()
}
