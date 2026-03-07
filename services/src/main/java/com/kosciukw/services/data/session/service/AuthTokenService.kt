package com.kosciukw.services.data.session.service

import com.kosciukw.services.data.session.model.AuthTokens

interface AuthTokenService {

    suspend fun getAccessToken(): String?

    suspend fun storeTokens(tokens: AuthTokens)

    suspend fun clearTokens()

    suspend fun refreshIfAccessTokenExpiring(leewaySeconds: Long = 90): Boolean

    suspend fun refreshAfterUnauthorized(): Boolean
}