package pl.kosciukw.petsify.shared.validator.email

class EmailValidatorImpl : EmailValidator {

    override fun isValid(data: CharArray): Boolean {
        return EMAIL_REGEX.matches(data.concatToString())
    }

    private companion object {
        // Deliberately pragmatic rather than RFC-complete; keeps validation platform-agnostic.
        val EMAIL_REGEX = Regex(
            pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
    }
}
