package com.kosciukw.services.internal.session.persistence

import com.kosciukw.services.internal.session.model.AuthTokens

interface TokenPersistence {

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun loadTokens(): AuthTokens?

    suspend fun updateAccessToken(accessToken: String)

    suspend fun clearTokens()
}