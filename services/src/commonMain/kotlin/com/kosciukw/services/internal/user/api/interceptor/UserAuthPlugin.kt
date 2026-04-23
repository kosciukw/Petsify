package com.kosciukw.services.internal.user.api.interceptor

import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.HttpRequestBuilder

class UserAuthPluginConfig {
    lateinit var authInterceptor: AuthInterceptor
    lateinit var authRequestRetrier: AuthRequestRetrier
}

val UserAuthPlugin = createClientPlugin(
    name = "UserAuthPlugin",
    createConfiguration = ::UserAuthPluginConfig
) {
    on(Send) { request: HttpRequestBuilder ->
        val authInterceptor = pluginConfig.authInterceptor
        val authRequestRetrier = pluginConfig.authRequestRetrier

        authInterceptor.appendAuthHeader(request)

        val call: HttpClientCall = proceed(request)
        val response = call.response
        if (!authRequestRetrier.shouldRetry(request, response)) {
            return@on call
        }

        val isRetried = authRequestRetrier.refreshAuthorization(request)
        if (!isRetried) {
            return@on call
        }

        proceed(request)
    }
}
