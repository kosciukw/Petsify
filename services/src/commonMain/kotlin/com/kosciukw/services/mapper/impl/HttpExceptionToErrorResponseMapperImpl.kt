package com.kosciukw.services.mapper.impl

import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class HttpExceptionToErrorResponseMapperImpl : HttpExceptionToErrorResponseMapper {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun map(input: ResponseException) = with(input) {
        runCatching {
            runBlocking {
                response.bodyAsText()
            }.takeUnless(String::isBlank)
                ?.let {
                    json.decodeFromString<ErrorResponse>(it)
                }
        }.getOrNull()
    }
}
