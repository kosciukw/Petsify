package com.kosciukw.services.data.auth.store.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kosciukw.services.data.auth.model.AuthTokens
import com.kosciukw.services.data.auth.di.AuthDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(
    @AuthDataStore private val dataStore: DataStore<Preferences>
) {
    private val keyAccessToken = stringPreferencesKey("access_token")
    private val keyRefreshToken = stringPreferencesKey("refresh_token")

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[keyAccessToken] = accessToken
            prefs[keyRefreshToken] = refreshToken
        }
    }

    suspend fun loadTokens(): AuthTokens? {
        val prefs = dataStore.data.first()
        val access = prefs[keyAccessToken]
        val refresh = prefs[keyRefreshToken]

        return if (access != null && refresh != null) {
            AuthTokens(
                accessToken = access,
                refreshToken = refresh
            )
        } else null
    }

    suspend fun updateAccessToken(accessToken: String) {
        dataStore.edit { prefs ->
            prefs[keyAccessToken] = accessToken
        }
    }

    suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(keyAccessToken)
            prefs.remove(keyRefreshToken)
        }
    }
}