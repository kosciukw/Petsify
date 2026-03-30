package com.kosciukw.services.mapper

import com.kosciukw.services.error.ErrorResponse
import io.ktor.client.plugins.ResponseException
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface HttpExceptionToErrorResponseMapper : ModelMapper<ResponseException, ErrorResponse?>
