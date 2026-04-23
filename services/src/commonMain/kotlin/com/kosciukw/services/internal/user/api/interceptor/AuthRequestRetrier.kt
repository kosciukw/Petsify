package com.kosciukw.services.internal.user.api.interceptor

import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import com.kosciukw.services.internal.session.service.AuthTokenService
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRequestRetrier(
    private val authSessionRepository: AuthSessionRepository
) : KoinComponent {

    private val authTokenService: AuthTokenService by inject()

    fun shouldRetry(
        request: HttpRequestBuilder,
        response: HttpResponse
    ): Boolean {
        if (request.headers["X-Bypass-Auth"] == "true") return false

        return response.status == HttpStatusCode.Unauthorized
    }

    suspend fun refreshAuthorization(request: HttpRequestBuilder): Boolean {
        val isRefreshed = authTokenService.refreshAfterUnauthorized()
        if (!isRefreshed) return false

        val newAccessToken = authSessionRepository.loadTokens()?.accessToken
        if (newAccessToken.isNullOrBlank()) return false

        request.headers.remove(HttpHeaders.Authorization)
        request.header(HttpHeaders.Authorization, "Bearer $newAccessToken")
        return true
    }
}
