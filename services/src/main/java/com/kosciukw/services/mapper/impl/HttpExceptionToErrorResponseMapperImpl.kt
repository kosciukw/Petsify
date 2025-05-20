package com.kosciukw.services.mapper.impl

import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import com.kosciukw.services.error.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException

class HttpExceptionToErrorResponseMapperImpl(
    private val retrofitErrorResponseBodyConverter: Converter<ResponseBody, ErrorResponse>
) : HttpExceptionToErrorResponseMapper {

    override fun map(input: HttpException) = runCatching {
        input.response()
            ?.errorBody()
            ?.let { errorBody ->
                retrofitErrorResponseBodyConverter.convert(errorBody)
            }
    }.getOrNull()
}