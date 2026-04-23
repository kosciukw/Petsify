package pl.kosciukw.petsify.shared.error

open class DomainError(override val message: String) : Throwable(message)