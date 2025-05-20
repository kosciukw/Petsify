package com.kosciukw.services.user.error

import android.content.Context
import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.data.user.service.user.error.UserDomainToAppErrorMapper
import com.kosciukw.services.data.user.service.user.error.impl.UserDomainToAppErrorMapperImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.ui.R as SharedR

internal class UserDomainToAppErrorMapperImplTest {

    private val context: Context = mockk(relaxed = true)
    private lateinit var mapper: UserDomainToAppErrorMapper

    @BeforeEach
    fun setUp() {
        mapper = UserDomainToAppErrorMapperImpl(context)
    }

    @Test
    fun `When error is authentication error Then should return info error with correct ui message`() {
        val givenMessage = "Invalid credentials"
        val expectedUiMessage = "Authentication failed"
        val expectedTechnicalMessage = "UserDomainError : Invalid credentials"

        val givenError = UserDomainError.AuthenticationError(message = givenMessage)

        every { context.getString(SharedR.string.error_authentication) } returns expectedUiMessage

        val result = mapper.map(givenError)

        assertTrue(result is AppError.InfoError)
        assertEquals(expectedTechnicalMessage, (result as AppError.InfoError).technicalMessage)
        assertEquals(expectedUiMessage, result.uiMessage)
    }
}