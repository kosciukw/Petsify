package pl.kosciukw.petsify.shared.validator.password

interface PasswordMatchValidator {
    fun isValid(
        password: CharArray,
        confirmPassword: CharArray
    ): PasswordMatchState
}