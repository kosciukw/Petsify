package com.kosciukw.services.data.session.repository

import com.kosciukw.services.data.session.model.AuthTokens

interface AuthSessionRepository {
    suspend fun loadTokens(): AuthTokens?
    suspend fun saveTokens(tokens: AuthTokens)
    suspend fun updateAccessToken(accessToken: String)
    suspend fun clearTokens()
}
