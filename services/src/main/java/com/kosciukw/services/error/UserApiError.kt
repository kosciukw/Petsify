package com.kosciukw.services.error

import pl.kosciukw.petsify.shared.utils.empty

sealed class UserApiError : Throwable() {

    data class HttpErrorUser(
        override val message: String? = null,
        val code: Int?,
        val reason: ErrorReasonDto
    ) : UserApiError() {
        override fun toString() = String.empty()
    }

    object RequestCancelled : UserApiError()

    data class ValidationError(
        override val message: String
    ) : UserApiError() {
        override fun toString() = String.empty()
    }

    data class AuthError(
        override val message: String
    ) : UserApiError() {
        override fun toString() = String.empty()
    }

    data class NotFoundError(
        override val message: String
    ) : UserApiError() {
        override fun toString() = String.empty()
    }

    data class HttpError(
        override val message: String? = null,
        val code: Int?
    ) : UserApiError() {
        override fun toString() = String.empty()
    }

    data class UnknownError(
        override val message: String? = null
    ) : UserApiError() {
        override fun toString() = String.empty()
    }
}