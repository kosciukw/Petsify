package pl.kosciukw.petsify.shared.ui.error

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.impl.CoreDomainToAppErrorMapperImpl
import pl.kosciukw.petsify.shared.ui.R as SharedR

internal class CoreDomainToAppErrorMapperImplTest {

    private lateinit var context: Context
    private lateinit var mapper: CoreDomainToAppErrorMapper

    @BeforeEach
    fun setUp() {
        context = mockk(relaxed = true)
        mapper = CoreDomainToAppErrorMapperImpl(context)
    }

    @Test
    fun `When error is no internet connection Then should return info error with correct ui message`() {
        val givenMessage = "No internet connection"
        val uiMessage = "Ui message"
        val givenError = CoreDomainError.NoInternetConnection(message = givenMessage)

        every { context.getString(SharedR.string.error_no_internet_connection) } returns uiMessage

        val result = mapper.map(givenError)

        assertTrue(result is AppError.InfoError)
        assertEquals(uiMessage, (result as AppError.InfoError).uiMessage)
    }

    @Test
    fun `When error is no session Then should return session expired error`() {
        val givenMessage = "Session has expired"
        val expectedMessage = "CoreDomainError : Session has expired"
        val givenError = CoreDomainError.NoSession(message = givenMessage)

        val result = mapper.map(givenError)

        assertTrue(result is AppError.TechnicalError.SessionExpired)
        assertEquals(expectedMessage, (result as AppError.TechnicalError.SessionExpired).message)
    }
}