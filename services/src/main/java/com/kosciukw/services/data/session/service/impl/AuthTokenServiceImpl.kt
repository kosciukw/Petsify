package com.kosciukw.services.data.session.service.impl

import com.kosciukw.services.data.session.model.AuthTokens
import com.kosciukw.services.data.session.repository.AuthSessionRepository
import com.kosciukw.services.data.session.service.AuthTokenService
import com.kosciukw.services.data.user.api.UserApi
import com.kosciukw.services.data.user.api.provider.UserUrlProvider
import com.kosciukw.services.data.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.data.user.model.api.request.RefreshRequest
import javax.inject.Inject

class AuthTokenServiceImpl @Inject constructor(
    private val authSessionRepository: AuthSessionRepository,
    private val userApi: UserApi,
    private val userUrlProvider: UserUrlProvider,
    private val userExceptionMapper: UserExceptionMapper //todo 14.09.2025
) : AuthTokenService {

    override suspend fun getAccessToken() = authSessionRepository.loadTokens()?.accessToken

    override suspend fun storeTokens(tokens: AuthTokens) {
        authSessionRepository.saveTokens(tokens)
    }

    override suspend fun clearTokens() {
        authSessionRepository.clearTokens()
    }

    override suspend fun refreshIfAccessTokenExpiring(leewaySeconds: Long): Boolean {
        val storedTokens = authSessionRepository.loadTokens() ?: return false

        val accessTokenExpiration = decodeJwtExpiration(storedTokens.accessToken) ?: return true
        val currentEpochSeconds = System.currentTimeMillis() / 1000

        if (accessTokenExpiration - currentEpochSeconds > leewaySeconds) return true

        return refreshWith(storedTokens.refreshToken)
    }

    override suspend fun refreshAfterUnauthorized(): Boolean {
        val storedTokens = authSessionRepository.loadTokens() ?: return false
        return refreshWith(storedTokens.refreshToken)
    }

    private suspend fun refreshWith(refreshToken: String): Boolean {
        if (refreshToken.isBlank()) return false

        return runCatching {
            userExceptionMapper.mapException {
                userApi.refreshToken(
                    url = userUrlProvider.getRefreshTokenUrl(),
                    refreshRequest = RefreshRequest(refreshToken)
                )
            }
        }.map { response ->
            if (!response.refreshToken.isNullOrBlank()) {
                authSessionRepository.saveTokens(
                    AuthTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken
                    )
                )
            } else authSessionRepository.updateAccessToken(accessToken = response.accessToken)

            true
        }.getOrElse {
            authSessionRepository.clearTokens()
            false
        }
    }

    private fun decodeJwtExpiration(jwt: String): Long? = runCatching {
        val payloadBase64 = jwt.split('.')[1]
        val payloadJson = String(
            android.util.Base64.decode(
                payloadBase64,
                android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
            )
        )
        org.json.JSONObject(payloadJson)
            .optLong("exp")
            .takeIf { it > 0 }
    }.getOrNull()
}