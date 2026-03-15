package pl.kosciukw.petsify.shared.mapper

import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError

interface DomainToAppErrorMapper {

    fun map(error: DomainError): AppError
}