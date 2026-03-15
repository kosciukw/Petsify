package com.kosciukw.services.internal.user.error.mapper.impl

import com.kosciukw.services.internal.user.error.mapper.HttpToUserApiExceptionMapper
import com.kosciukw.services.internal.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.error.UserApiError
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

class UserExceptionMapperImpl(
    private val httpToUserApiExceptionMapper: HttpToUserApiExceptionMapper
) : UserExceptionMapper {

    override suspend fun <T> mapException(block: suspend () -> T) =
        runCatching { block() }.getOrElse { error ->
            throw when (error) {
                is HttpException -> httpToUserApiExceptionMapper.map(error)
                is CancellationException -> UserApiError.RequestCancelled
                else -> UserApiError.UnknownError(
                    message = error.message
                )
            }
        }
}