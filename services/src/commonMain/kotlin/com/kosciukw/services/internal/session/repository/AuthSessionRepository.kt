package com.kosciukw.services.internal.session.repository

import com.kosciukw.services.internal.session.model.AuthTokens

interface AuthSessionRepository {
    suspend fun loadTokens(): AuthTokens?
    suspend fun saveTokens(tokens: AuthTokens)
    suspend fun updateAccessToken(accessToken: String)
    suspend fun clearTokens()
}
