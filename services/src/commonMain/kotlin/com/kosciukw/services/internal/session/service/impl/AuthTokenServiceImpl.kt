package com.kosciukw.services.internal.session.service.impl

import com.kosciukw.services.internal.session.model.AuthTokens
import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.session.time.currentEpochSeconds
import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.api.provider.UserUrlProvider
import com.kosciukw.services.internal.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AuthTokenServiceImpl(
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
        val currentEpochSeconds = currentEpochSeconds()

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

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeJwtExpiration(jwt: String): Long? = runCatching {
        val payloadBase64 = jwt.split('.')[1]
        val normalizedPayload = payloadBase64.padEnd(
            length = ((payloadBase64.length + 3) / 4) * 4,
            padChar = '='
        )
        val payloadJson = Base64.UrlSafe.decode(normalizedPayload).decodeToString()
        val expiration = Json.parseToJsonElement(payloadJson)
            .jsonObject["exp"]
            ?.jsonPrimitive
            ?.content
            ?.toLongOrNull()

        expiration?.takeIf { it > 0 }
    }.getOrNull()
}
