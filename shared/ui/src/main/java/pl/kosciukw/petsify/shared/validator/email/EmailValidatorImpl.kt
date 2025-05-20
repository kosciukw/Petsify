package pl.kosciukw.petsify.shared.validator.email

import android.util.Patterns
import java.nio.CharBuffer

class EmailValidatorImpl : EmailValidator {
    
    override fun isValid(data: CharArray): Boolean {
        val buffer = CharBuffer.wrap(data)
        return Patterns.EMAIL_ADDRESS.matcher(buffer)
            .matches()
            .also { buffer.clear() }
    }
}