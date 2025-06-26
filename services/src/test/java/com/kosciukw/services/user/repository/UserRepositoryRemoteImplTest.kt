package com.kosciukw.services.user.repository

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.repository.impl.UserRepositoryRemoteImpl
import io.mockk.coVerify
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.network.NetworkStateProvider

internal class UserRepositoryRemoteImplTest {

    private lateinit var repository: UserRepository
    private val userApiController: UserApiController = mockk(relaxed = true)
    private val errorMapper: UserApiToDomainErrorMapper = mockk(relaxed = true)
    private val networkStateProvider: NetworkStateProvider = mockk(relaxed = true)
    private val loginByPasswordDomainToRequestModelMapper: LoginByPasswordDomainToRequestModelMapper = mockk(relaxed = true)
    private val signUpDomainToRequestModelMapper: SignUpDomainToRequestModelMapper = mockk(relaxed = true)
    private val startOtpRegistrationDomainToRequestModelMapper: StartOtpRegistrationDomainToRequestModelMapper = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        repository = UserRepositoryRemoteImpl(
            networkStateProvider = networkStateProvider,
            errorMapper = errorMapper,
            userApiController = userApiController,
            loginByPasswordDomainToRequestModelMapper = loginByPasswordDomainToRequestModelMapper,
            signUpDomainToRequestModelMapper = signUpDomainToRequestModelMapper,
            startOtpRegistrationDomainToRequestModelMapper = startOtpRegistrationDomainToRequestModelMapper
        )
    }

    @Test
    fun `When login device called Then should call proper method in controller`() {
        //Given
        val givenLoginByPasswordDomainModel = LoginByPasswordDomainModel(
            email = "email",
            password = "password"
        )

        val givenLoginDeviceByPasswordRequest = LoginByPasswordRequest(
            email = "email",
            password = "password"
        )

        every {
            networkStateProvider.isInternetConnectionAvailable()
        } returns true
        every {
            loginByPasswordDomainToRequestModelMapper.map(givenLoginByPasswordDomainModel)
        } returns givenLoginDeviceByPasswordRequest

        //When
        runBlocking {
            repository.loginDeviceByPassword(givenLoginByPasswordDomainModel)
        }

        //Then
        coVerify { userApiController.loginByPassword(givenLoginDeviceByPasswordRequest) }
    }

    @Test
    fun `When login device called And no internet connection Then should throw proper exception`() {
        // Given
        val givenLoginByPasswordDomainModel = LoginByPasswordDomainModel(
            email = "email",
            password = "password"
        )

        every { networkStateProvider.isInternetConnectionAvailable() } returns false

        // When & Then
        assertThrows<CoreDomainError.NoInternetConnection> {
            runBlocking {
                repository.loginDeviceByPassword(givenLoginByPasswordDomainModel)
            }
        }

        coVerify(exactly = 0) { userApiController.loginByPassword(any()) }
    }
}