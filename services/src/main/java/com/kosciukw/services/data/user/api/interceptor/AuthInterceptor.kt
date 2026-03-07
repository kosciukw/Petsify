package com.kosciukw.services.data.user.api.interceptor

import com.kosciukw.services.data.session.repository.AuthSessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authSessionRepository: AuthSessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        if (req.header("X-Bypass-Auth") == "true") {
            return chain.proceed(req)
        }

        val token = runBlocking { authSessionRepository.loadTokens() }?.accessToken
        val newReq = if (!token.isNullOrBlank()) {
            req.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else req

        return chain.proceed(newReq)
    }
}