package pl.kosciukw.petsify.shared.ui.validator
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

internal class NotEmptyValidatorTest {

    private lateinit var validator: NotEmptyValidator<Any>

    @BeforeEach
    fun setUp() {
        validator = NotEmptyValidator()
    }

    @Test
    fun `When data is non-empty CharSequence Then should return true`() {
        val result = validator.isValid("abc")
        assertTrue(result)
    }

    @Test
    fun `When data is empty CharSequence Then should return false`() {
        val result = validator.isValid("")
        assertFalse(result)
    }

    @Test
    fun `When data is non-empty CharArray Then should return true`() {
        val result = validator.isValid(charArrayOf('a'))
        assertTrue(result)
    }

    @Test
    fun `When data is empty CharArray Then should return false`() {
        val result = validator.isValid(charArrayOf())
        assertFalse(result)
    }
}