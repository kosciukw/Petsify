package com.kosciukw.services.mapper.impl

import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import com.google.gson.Gson
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking

class HttpExceptionToErrorResponseMapperImpl(
    private val gson: Gson
) : HttpExceptionToErrorResponseMapper {

    override fun map(input: ResponseException) = with(input) {
        runCatching {
            runBlocking {
                response.bodyAsText()
            }.takeUnless(String::isBlank)
                ?.let {
                    gson.fromJson(it, ErrorResponse::class.java)
                }
        }.getOrNull()
    }
}
