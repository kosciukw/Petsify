package pl.kosciukw.petsify.feature.pairdevice.validator

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidator
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

class EmailIdentifierValidatorImplTest {

    private val notEmptyValidator: NotEmptyValidator<CharArray> = mockk()
    private val emailValidator: EmailValidator = mockk()

    private lateinit var validator: EmailIdentifierValidator

    @BeforeEach
    fun setUp() {
        validator = EmailIdentifierValidatorImpl(emailValidator, notEmptyValidator)
    }

    @Test
    fun `When validator is called and value is not empty Then should validate properly`() {
        val email = charArrayOf()

        every { notEmptyValidator.isValid(email) } returns false

        val result = validator.isValid(EmailIdentifier(email))

        assertEquals(IdentifierState.Empty, result)
        verify { notEmptyValidator.isValid(email) }
    }

    @Test
    fun `When validator is called and value is not empty but invalid Then should return Invalid`() {
        val email = "invalid_email".toCharArray()

        every { notEmptyValidator.isValid(email) } returns true
        every { emailValidator.isValid(email) } returns false

        val result = validator.isValid(EmailIdentifier(email))

        assertEquals(IdentifierState.Invalid, result)
        verify { notEmptyValidator.isValid(email) }
        verify { emailValidator.isValid(email) }
    }

    @Test
    fun `When validator is called and value is not empty and valid Then should return Valid`() {
        val email = "user@example.com".toCharArray()

        every { notEmptyValidator.isValid(email) } returns true
        every { emailValidator.isValid(email) } returns true

        val result = validator.isValid(EmailIdentifier(email))

        assertEquals(IdentifierState.Valid, result)
        verify { notEmptyValidator.isValid(email) }
        verify { emailValidator.isValid(email) }
    }
}