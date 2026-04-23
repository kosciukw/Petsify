package com.kosciukw.services.internal.user.api.creator

import com.kosciukw.services.internal.user.api.interceptor.AuthInterceptor
import com.kosciukw.services.internal.user.api.interceptor.AuthRequestRetrier
import com.kosciukw.services.internal.user.api.interceptor.UserAuthPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal actual fun createPlatformHttpClient(
    authInterceptor: AuthInterceptor,
    authRequestRetrier: AuthRequestRetrier
): HttpClient = HttpClient(Darwin) {
    expectSuccess = true

    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(DefaultRequest) {
        url("http://localhost:8080/")
        contentType(ContentType.Application.Json)
    }

    install(HttpTimeout) {
        connectTimeoutMillis = 20_000
        requestTimeoutMillis = 20_000
        socketTimeoutMillis = 20_000
    }

    addDefaultResponseValidation()

    install(UserAuthPlugin) {
        this.authInterceptor = authInterceptor
        this.authRequestRetrier = authRequestRetrier
    }
}
