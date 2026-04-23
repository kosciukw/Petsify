package com.kosciukw.services.internal.user.api.creator

import com.kosciukw.services.internal.user.api.interceptor.AuthInterceptor
import com.kosciukw.services.internal.user.api.interceptor.AuthRequestRetrier
import io.ktor.client.HttpClient

internal expect fun createPlatformHttpClient(
    authInterceptor: AuthInterceptor,
    authRequestRetrier: AuthRequestRetrier
): HttpClient
