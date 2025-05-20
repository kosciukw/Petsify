package com.kosciukw.services.error

import com.google.gson.annotations.SerializedName
import pl.kosciukw.petsify.shared.utils.empty

data class ErrorResponse(
    @SerializedName("errors")
    val errors: List<String>,

    @SerializedName("reason")
    val reason: String
) {
    override fun toString() = String.empty()

    val errorReason: ErrorReasonDto
        get() = ErrorReasonDto.safeValueOf(reason)
}