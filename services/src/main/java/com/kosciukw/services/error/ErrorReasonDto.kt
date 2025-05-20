package com.kosciukw.services.error

enum class ErrorReasonDto(
    private val rawValue: String
) {
    AUTH_ERROR("AUTH_ERROR"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    NOT_FOUND("NOT_FOUND"),
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    companion object {
        fun safeValueOf(rawValue: String): ErrorReasonDto {
            return entries.find { it.rawValue == rawValue } ?: UNKNOWN_ERROR
        }
    }
}