package pl.kosciukw.petsify.shared.validator.password

class PasswordValidatorImpl : PasswordValidator {

    override fun isValid(data: CharArray): Boolean {
        val password = data.concatToString()
        return password.matches(Regex(PASSWORD_REGEX))
    }

    companion object {
        private const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$"
    }
}