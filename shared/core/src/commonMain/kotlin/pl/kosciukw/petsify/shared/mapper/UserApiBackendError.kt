package pl.kosciukw.petsify.shared.mapper

import pl.kosciukw.petsify.shared.error.DomainError

class TmpError(message: String) : Throwable(message)

typealias UserApiBackendError = TmpError

interface UserApiBackendToDomainErrorMapper : ModelMapper<UserApiBackendError, DomainError>