package pl.kosciukw.petsify.shared.validator.password

class PasswordMatchValidatorImpl : PasswordMatchValidator {

    override fun isValid(password: CharArray, confirmPassword: CharArray): PasswordMatchState {
        return when {
            password.isEmpty() || confirmPassword.isEmpty() -> PasswordMatchState.Empty
            password.contentEquals(confirmPassword) -> PasswordMatchState.Match
            else -> PasswordMatchState.Mismatch
        }
    }
}
