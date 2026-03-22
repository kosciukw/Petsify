package com.kosciukw.services.internal.user.error.mapper.impl

import com.kosciukw.services.internal.user.error.mapper.ErrorResponseToUserApiExceptionMapper
import com.kosciukw.services.internal.user.error.mapper.HttpToUserApiExceptionMapper
import com.kosciukw.services.error.UserApiError
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import io.ktor.client.plugins.ResponseException

class HttpToUserApiExceptionMapperImpl(
    private val httpExceptionToErrorResponseMapper: HttpExceptionToErrorResponseMapper,
    private val errorResponseToUserApiExceptionMapper: ErrorResponseToUserApiExceptionMapper
) : HttpToUserApiExceptionMapper {

    override fun map(input: ResponseException) = with(input) {
        httpExceptionToErrorResponseMapper.map(input = this)
            ?.let { errorResponse -> errorResponseToUserApiExceptionMapper.map(input = errorResponse) }
            ?: UserApiError.HttpError(
                code = response.status.value,
                message = message
            )
    }
}
