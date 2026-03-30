package com.kosciukw.services.internal.session.service

import com.kosciukw.services.internal.session.model.AuthTokens

interface AuthTokenService {

    suspend fun getAccessToken(): String?

    suspend fun storeTokens(tokens: AuthTokens)

    suspend fun clearTokens()

    suspend fun refreshIfAccessTokenExpiring(leewaySeconds: Long = 90): Boolean

    suspend fun refreshAfterUnauthorized(): Boolean
}