package com.kosciukw.services.mapper

import com.kosciukw.services.error.ErrorResponse
import pl.kosciukw.petsify.shared.mapper.ModelMapper
import retrofit2.HttpException

interface HttpExceptionToErrorResponseMapper : ModelMapper<HttpException, ErrorResponse?>