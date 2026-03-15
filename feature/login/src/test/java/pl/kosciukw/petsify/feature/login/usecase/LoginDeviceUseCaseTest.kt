package pl.kosciukw.petsify.feature.login.usecase

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.user.error.UserDomainError
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlinx.coroutines.flow.toList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import pl.kosciukw.petsify.shared.result.ResultOrFailure

internal class LoginDeviceUseCaseTest {

    private val authService: AuthService = mockk()

    private lateinit var loginDeviceUseCase: LoginDeviceUseCase

    @BeforeEach
    fun setup() {
        loginDeviceUseCase = LoginDeviceUseCase(authService)
    }

    @Test
    fun `when login fails then emit loading and failure`() = runTest {
        // Given
        val params = LoginDeviceUseCase.Params(
            email = "email@test.com",
            password = "wrongpassword"
        )
        val exception = UserDomainError.AuthenticationError(
            message = "AuthenticationError"
        )

        coEvery {
            authService.loginDeviceByPassword(any())
        } throws exception

        // When
        val result = loginDeviceUseCase.action(params).toList()

        // Then
        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Failure(exception), result[1])
    }

    @Test
    fun `when login is successful then emit loading and success`() = runTest {
        // Given
        val params = LoginDeviceUseCase.Params(
            email = "email@test.com",
            password = "correctpassword"
        )

        val expectedAccessToken = AuthSessionDomainModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        coEvery {
            authService.loginDeviceByPassword(any())
        } returns expectedAccessToken

        // When
        val result = loginDeviceUseCase.action(params).toList()

        // Then
        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Success(expectedAccessToken), result[1])
    }
}
