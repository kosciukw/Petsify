package com.kosciukw.services.data.session.persistence.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kosciukw.services.data.session.model.AuthTokens
import com.kosciukw.services.data.session.persistence.TokenPersistence
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenPersistenceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenPersistence {

    private val keyAccessToken = stringPreferencesKey(ACCESS_TOKEN_KEY)
    private val keyRefreshToken = stringPreferencesKey(REFRESH_TOKEN_KEY)

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[keyAccessToken] = accessToken
            prefs[keyRefreshToken] = refreshToken
        }
    }

    override suspend fun loadTokens(): AuthTokens? {
        val prefs = dataStore.data.first()
        val accessToken = prefs[keyAccessToken]
        val refreshToken = prefs[keyRefreshToken]

        return if (accessToken != null && refreshToken != null) {
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } else null
    }

    override suspend fun updateAccessToken(accessToken: String) {
        dataStore.edit { prefs ->
            prefs[keyAccessToken] = accessToken
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(keyAccessToken)
            prefs.remove(keyRefreshToken)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "99279873"
        private const val REFRESH_TOKEN_KEY = "57772f6e"
    }
}