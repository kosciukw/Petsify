package com.kosciukw.services.internal.user.error.mapper

import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.error.UserApiError
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface ErrorResponseToUserApiExceptionMapper : ModelMapper<ErrorResponse, UserApiError>