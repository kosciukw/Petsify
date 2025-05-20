package com.kosciukw.services.data.user.error.mapper.impl

import com.kosciukw.services.data.user.error.mapper.ErrorResponseToUserApiExceptionMapper
import com.kosciukw.services.error.ErrorReasonDto
import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.error.UserApiError

class ErrorResponseToUserApiExceptionMapperImpl : ErrorResponseToUserApiExceptionMapper {

    override fun map(input: ErrorResponse) =
        when (input.errorReason) {
            ErrorReasonDto.AUTH_ERROR -> UserApiError.AuthError(message = input.reason)
            ErrorReasonDto.VALIDATION_ERROR -> UserApiError.ValidationError(message = input.reason)
            ErrorReasonDto.NOT_FOUND -> UserApiError.NotFoundError(message = input.reason)
            ErrorReasonDto.UNKNOWN_ERROR -> UserApiError.UnknownError(message = input.reason)
        }
}