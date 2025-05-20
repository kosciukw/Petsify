package com.kosciukw.services.data.user.error.mapper

import com.kosciukw.services.error.UserApiError
import pl.kosciukw.petsify.shared.mapper.ModelMapper
import retrofit2.HttpException

interface HttpToUserApiExceptionMapper : ModelMapper<HttpException, UserApiError>