package com.kosciukw.services.error

import kotlinx.serialization.Serializable
import pl.kosciukw.petsify.shared.utils.empty

@Serializable
data class ErrorResponse(
    val errors: List<String>,
    val reason: String
) {
    override fun toString() = String.empty()

    val errorReason: ErrorReasonDto
        get() = ErrorReasonDto.safeValueOf(reason)
}
