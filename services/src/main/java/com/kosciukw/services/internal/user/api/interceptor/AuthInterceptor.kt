package com.kosciukw.services.internal.user.api.interceptor

import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class AuthInterceptor(
    private val authSessionRepository: AuthSessionRepository
) {

    suspend fun appendAuthHeader(request: HttpRequestBuilder) {
        if (request.headers["X-Bypass-Auth"] == "true") return

        val token = authSessionRepository.loadTokens()?.accessToken
        if (token.isNullOrBlank()) return

        request.headers.remove(HttpHeaders.Authorization)
        request.header(HttpHeaders.Authorization, "Bearer $token")
    }
}
