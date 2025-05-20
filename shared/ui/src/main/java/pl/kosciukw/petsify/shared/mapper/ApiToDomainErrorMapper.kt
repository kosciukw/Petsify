package pl.kosciukw.petsify.shared.mapper

import pl.kosciukw.petsify.shared.error.DomainError

interface ApiToDomainErrorMapper {
    
    fun map(exception: Throwable): DomainError
}