package pl.kosciukw.petsify.error

import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.data.user.service.user.error.UserDomainToAppErrorMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.error.mapper.AppIntegrationDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper

internal class AppIntegrationDomainToAppErrorMapperTest {

    private val userDomainErrorMapper: UserDomainToAppErrorMapper = mockk()
    private val coreDomainToAppErrorMapper: CoreDomainToAppErrorMapper = mockk()
    private lateinit var appIntegrationDomainToAppErrorMapper: AppIntegrationDomainToAppErrorMapper

    @BeforeEach
    fun setUp() {
        appIntegrationDomainToAppErrorMapper = AppIntegrationDomainToAppErrorMapper(
            userDomainErrorMapper,
            coreDomainToAppErrorMapper
        )
    }

    @Test
    fun `When domain error is passed Then should map using user domain mapper`() {
        val givenError = mockk<UserDomainError>()
        val expectedResult = mockk<AppError>()

        every {
            userDomainErrorMapper.map(givenError)
        } returns expectedResult

        val result = appIntegrationDomainToAppErrorMapper.map(givenError)

        assertEquals(expectedResult, result)
        verify { userDomainErrorMapper.map(givenError) }
        verify(exactly = 0) { coreDomainToAppErrorMapper.map(any()) }
    }

    @Test
    fun `When core domain error is passed Then should map using core domain mapper`() {
        val givenError = mockk<CoreDomainError>()
        val expectedResult = mockk<AppError>()

        every {
            coreDomainToAppErrorMapper.map(givenError)
        } returns expectedResult

        val result = appIntegrationDomainToAppErrorMapper.map(givenError)

        assertEquals(expectedResult, result)
        verify { coreDomainToAppErrorMapper.map(givenError) }
        verify(exactly = 0) { userDomainErrorMapper.map(any()) }
    }

    @Test
    fun `When unknown error is passed Then should return technical unknown`() {
        val givenError = Throwable("Unknown error")

        val result = appIntegrationDomainToAppErrorMapper.map(givenError)

        assertTrue(result is AppError.TechnicalError.Unknown)
        verify(exactly = 0) { userDomainErrorMapper.map(any()) }
        verify(exactly = 0) { coreDomainToAppErrorMapper.map(any()) }
    }
}