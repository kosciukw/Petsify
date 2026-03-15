package pl.kosciukw.petsify.feature.otp.usecase

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.registration.RegistrationService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.shared.result.ResultOrFailure

internal class FinalizeOtpRegistrationUseCaseTest {

    private val registrationService: RegistrationService = mockk()

    private lateinit var useCase: FinalizeOtpRegistrationUseCase

    @BeforeEach
    fun setUp() {
        useCase = FinalizeOtpRegistrationUseCase(registrationService)
    }

    @Test
    fun `when finalize otp is successful then emit loading and success`() = runTest {
        val params = FinalizeOtpRegistrationUseCase.Params(
            email = "john@petsify.com",
            password = "Password123",
            name = "John",
            termsAccepted = true,
            marketingAccepted = true,
            otp = "123456"
        )
        val expected = AuthSessionDomainModel(
            accessToken = "access-token",
            refreshToken = "refresh-token"
        )

        coEvery { registrationService.finalizeOtpRegistration(any()) } returns expected

        val result = useCase.action(params).toList()

        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Success(expected), result[1])
    }

    @Test
    fun `when finalize otp fails then emit loading and failure`() = runTest {
        val params = FinalizeOtpRegistrationUseCase.Params(
            email = "john@petsify.com",
            password = "Password123",
            name = "John",
            termsAccepted = true,
            marketingAccepted = true,
            otp = "123456"
        )
        val expectedError = IllegalStateException("OTP failed")

        coEvery { registrationService.finalizeOtpRegistration(any()) } throws expectedError

        val result = useCase.action(params).toList()

        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Failure(expectedError), result[1])
    }
}
