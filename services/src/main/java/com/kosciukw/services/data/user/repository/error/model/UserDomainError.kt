package com.kosciukw.services.data.user.repository.error.model

import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.utils.empty

sealed class UserDomainError(message: String) : DomainError(message = HEADER + message) {

    class ApiRequestFailed(message: String) : UserDomainError(message = message)

    class UserNotVerified(message: String) : UserDomainError(message = message)

    class CannotFindUser(message: String) : UserDomainError(message = message)

    class ValidationError(message: String) : UserDomainError(message)

    class InvalidOtpCode(message: String) : UserDomainError(message)

    class InvalidOtpCodeLimitReached(message: String) : UserDomainError(message)

    class AddingUserFailed : UserDomainError(message = "Adding user failed")

    class AuthenticationError(message: String) : UserDomainError(message)

    class UserLocked : UserDomainError(message = "User is locked")

    class UnknownError(message: String) : UserDomainError(message)

    override fun toString() = String.empty()


    companion object {
        private const val HEADER = "UserDomainError : "
    }
}