package com.kosciukw.services.internal.user.error.mapper.impl

import com.kosciukw.services.internal.user.error.mapper.ErrorResponseToUserApiExceptionMapper
import com.kosciukw.services.internal.user.error.mapper.HttpToUserApiExceptionMapper
import com.kosciukw.services.error.UserApiError
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import retrofit2.HttpException

class HttpToUserApiExceptionMapperImpl(
    private val httpExceptionToErrorResponseMapper: HttpExceptionToErrorResponseMapper,
    private val errorResponseToUserApiExceptionMapper: ErrorResponseToUserApiExceptionMapper
) : HttpToUserApiExceptionMapper {

    override fun map(input: HttpException) = with(input) {
        httpExceptionToErrorResponseMapper.map(input = this)
            ?.let { errorResponse -> errorResponseToUserApiExceptionMapper.map(input = errorResponse) }
            ?: UserApiError.HttpError(
                code = code(),
                message = message()
            )
    }
}