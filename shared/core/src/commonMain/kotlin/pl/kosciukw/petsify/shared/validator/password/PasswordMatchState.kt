package pl.kosciukw.petsify.shared.validator.password

sealed class PasswordMatchState {
    data object Match : PasswordMatchState()
    data object Mismatch : PasswordMatchState()
    data object Empty : PasswordMatchState()
}