package com.kosciukw.services.internal.user.api.authenticator

import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import com.kosciukw.services.internal.session.service.AuthTokenService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val authSessionRepository: AuthSessionRepository,
    private val authTokenServiceProvider: Provider<AuthTokenService>
) : Authenticator {

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request? {
        if (responseCount(response) >= 2) return null
        if (response.request.header("X-Bypass-Auth") == "true") return null

        val isRefreshed = runBlocking { authTokenServiceProvider.get().refreshAfterUnauthorized() }
        if (!isRefreshed) return null

        val newAccessToken = runBlocking { authSessionRepository.loadTokens()?.accessToken }
        if (newAccessToken.isNullOrBlank()) return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var r: Response? = response
        while (r?.priorResponse != null) {
            r = r.priorResponse
            count++
        }
        return count
    }
}
