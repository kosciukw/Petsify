package com.kosciukw.services.data.session.persistence

import com.kosciukw.services.data.session.model.AuthTokens

interface TokenPersistence {

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun loadTokens(): AuthTokens?

    suspend fun updateAccessToken(accessToken: String)

    suspend fun clearTokens()
}