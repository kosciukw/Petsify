package pl.kosciukw.petsify.iosapp

import com.kosciukw.services.internal.session.model.AuthTokens
import com.kosciukw.services.internal.session.persistence.TokenPersistence
import platform.Foundation.NSUserDefaults

internal class IosTokenPersistence(
    private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults
) : TokenPersistence {

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        userDefaults.setObject(accessToken, ACCESS_TOKEN_KEY)
        userDefaults.setObject(refreshToken, REFRESH_TOKEN_KEY)
    }

    override suspend fun loadTokens(): AuthTokens? {
        val accessToken = userDefaults.stringForKey(ACCESS_TOKEN_KEY)
        val refreshToken = userDefaults.stringForKey(REFRESH_TOKEN_KEY)

        return if (accessToken != null && refreshToken != null) {
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } else {
            null
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        userDefaults.setObject(accessToken, ACCESS_TOKEN_KEY)
    }

    override suspend fun clearTokens() {
        userDefaults.removeObjectForKey(ACCESS_TOKEN_KEY)
        userDefaults.removeObjectForKey(REFRESH_TOKEN_KEY)
    }

    private companion object {
        private const val ACCESS_TOKEN_KEY = "99279873"
        private const val REFRESH_TOKEN_KEY = "57772f6e"
    }
}
