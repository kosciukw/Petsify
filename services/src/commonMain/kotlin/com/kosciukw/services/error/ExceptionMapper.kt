package com.kosciukw.services.error

interface ExceptionMapper {
    suspend fun <T> mapException(block: suspend () -> T): T
}