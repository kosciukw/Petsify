package pl.kosciukw.petsify.shared.validator

sealed class IdentifierState {
    
    object Empty : IdentifierState()

    object Valid : IdentifierState()

    object Invalid : IdentifierState()
}