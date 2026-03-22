package com.kosciukw.services.internal.user.error.mapper

import com.kosciukw.services.error.UserApiError
import io.ktor.client.plugins.ResponseException
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface HttpToUserApiExceptionMapper : ModelMapper<ResponseException, UserApiError>
