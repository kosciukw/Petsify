package pl.kosciukw.petsify.shared.validator

interface IdentifierValidator<T : Identifier> {
    
    fun isValid(identifier: T): IdentifierState
}