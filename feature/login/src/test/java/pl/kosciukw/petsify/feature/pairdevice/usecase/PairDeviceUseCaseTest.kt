package pl.kosciukw.petsify.feature.pairdevice.usecase

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.data.user.service.user.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlinx.coroutines.flow.toList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import pl.kosciukw.petsify.shared.result.ResultOrFailure

internal class PairDeviceUseCaseTest {

    private val userService: UserService = mockk()

    private lateinit var pairDeviceUseCase: PairDeviceUseCase

    @BeforeEach
    fun setup() {
        pairDeviceUseCase = PairDeviceUseCase(userService)
    }

    @Test
    fun `when login fails then emit loading and failure`() = runTest {
        // Given
        val params = PairDeviceUseCase.Params(
            email = "email@test.com",
            password = "wrongpassword"
        )
        val exception = UserDomainError.AuthenticationError(
            message = "AuthenticationError"
        )

        coEvery {
            userService.pairDeviceByPassword(any())
        } throws exception

        // When
        val result = pairDeviceUseCase.action(params).toList()

        // Then
        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Failure(exception), result[1])
    }

    @Test
    fun `when login is successful then emit loading and success`() = runTest {
        // Given
        val params = PairDeviceUseCase.Params(
            email = "email@test.com",
            password = "correctpassword"
        )

        val expectedAccessToken = AccessTokenApiModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        coEvery {
            userService.pairDeviceByPassword(any())
        } returns expectedAccessToken

        // When
        val result = pairDeviceUseCase.action(params).toList()

        // Then
        assertEquals(ResultOrFailure.Loading, result[0])
        assertEquals(ResultOrFailure.Success(expectedAccessToken), result[1])
    }
}