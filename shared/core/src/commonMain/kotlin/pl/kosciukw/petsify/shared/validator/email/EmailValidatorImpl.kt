package pl.kosciukw.petsify.shared.validator.email

import java.nio.CharBuffer

class EmailValidatorImpl : EmailValidator {

    override fun isValid(data: CharArray): Boolean {
        val buffer = CharBuffer.wrap(data)
        return EMAIL_REGEX.matches(buffer)
            .also { buffer.clear() }
    }

    private companion object {
        // Deliberately pragmatic rather than RFC-complete; keeps validation platform-agnostic.
        val EMAIL_REGEX = Regex(
            pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
    }
}