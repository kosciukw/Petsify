package pl.kosciukw.petsify.shared.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@PublishedApi
internal val navArgsJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

inline fun <reified T : TypedNavArgs> navArgsType(): NavType<T> =
    object : NavType<T>(isNullableAllowed = false) {
        override fun serializeAsValue(value: T): String {
            val raw = navArgsJson.encodeToString(value)
            return Uri.encode(raw)
        }

        override fun parseValue(value: String): T {
            val decoded = Uri.decode(value)
            return navArgsJson.decodeFromString(decoded)
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putString(key, serializeAsValue(value))
        }

        override fun get(bundle: Bundle, key: String): T? {
            val encoded = bundle.getString(key) ?: return null
            return parseValue(encoded)
        }
    }

inline fun <reified T : TypedNavArgs> typeMapEntry(): Pair<KType, NavType<T>> =
    typeOf<T>() to navArgsType<T>()
