package pl.kosciukw.petsify.shared.error.mapper

import pl.kosciukw.petsify.shared.error.AppError

interface IntegrationErrorMapper {

    fun map(error: Throwable): AppError
}