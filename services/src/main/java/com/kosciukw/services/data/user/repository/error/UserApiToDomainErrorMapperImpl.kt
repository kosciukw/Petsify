package com.kosciukw.services.data.user.repository.error

import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.error.UserApiError
import pl.kosciukw.petsify.shared.error.CoreDomainError

class UserApiToDomainErrorMapperImpl : UserApiToDomainErrorMapper {

    override fun map(exception: Throwable) = when (exception) {
        is UserApiError -> wrapException(error = exception)
        else -> UserDomainError.UnknownError(
            message = exception.message ?: "Unknown error occurred"
        )
    }

    private fun wrapException(error: UserApiError) = when (error) {
        is UserApiError.HttpError -> UserDomainError.ApiRequestFailed(
            message = error.message ?: ("Error while making a request with status " + error.code)
        )

        is UserApiError.RequestCancelled -> CoreDomainError.RequestCancelled(
            message = error.message ?: "Http request cancelled"
        )

        is UserApiError.ValidationError -> UserDomainError.ValidationError(
            message = error.message
        )

        is UserApiError.EmailAlreadyRegistered -> UserDomainError.EmailAlreadyRegistered(
            message = error.message
        )

        is UserApiError.AuthError -> UserDomainError.AuthenticationError(
            message = error.message
        )

        else -> UserDomainError.UnknownError(
            message = error.message ?: "Unknown error occurred"
        )

//        is UserApiError.HttpErrorUser -> TODO()
//        is UserApiError.NotFoundError -> TODO()
    }
}