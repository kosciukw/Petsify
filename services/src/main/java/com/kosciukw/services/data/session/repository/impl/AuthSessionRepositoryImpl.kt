package com.kosciukw.services.data.session.repository.impl

import com.kosciukw.services.data.session.model.AuthTokens
import com.kosciukw.services.data.session.repository.AuthSessionRepository
import com.kosciukw.services.data.session.persistence.TokenPersistence
import javax.inject.Inject

class AuthSessionRepositoryImpl @Inject constructor(
    private val tokenPersistence: TokenPersistence
) : AuthSessionRepository {

    override suspend fun loadTokens(): AuthTokens? {
        val token = tokenPersistence.loadTokens() ?: return null
        return AuthTokens(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        )
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        tokenPersistence.saveTokens(tokens.accessToken, tokens.refreshToken)
    }

    override suspend fun updateAccessToken(accessToken: String) {
        tokenPersistence.updateAccessToken(accessToken)
    }

    override suspend fun clearTokens() {
        tokenPersistence.clearTokens()
    }
}