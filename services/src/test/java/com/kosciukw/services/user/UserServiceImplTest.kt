package com.kosciukw.services.user

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.user.mapper.AccessTokenApiToAuthSessionDomainModelMapper
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.internal.user.repository.UserRepository
import com.kosciukw.services.internal.user.service.UserServiceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserServiceImplTest {

    private lateinit var service: AuthService

    private val userRepository: UserRepository = mockk()
    private val authTokenService: AuthTokenService = mockk()
    private val accessTokenApiToAuthSessionDomainModelMapper: AccessTokenApiToAuthSessionDomainModelMapper = mockk()

    @BeforeEach
    fun setUp() {
        service = UserServiceImpl(
            userRepository = userRepository,
            authTokenService = authTokenService,
            accessTokenApiToAuthSessionDomainModelMapper = accessTokenApiToAuthSessionDomainModelMapper
        )
    }

    @Test
    fun `When login device called Then should call proper method in repository`() = runTest {
        //Given
        val givenLoginByPasswordDomainModel = LoginByPasswordDomainModel(
            email = "email",
            password = "password"
        )
        val expectedResponse = AccessTokenApiModel(
            accessToken = "access-token",
            refreshToken = "refresh-token"
        )
        val expectedDomainModel = AuthSessionDomainModel(
            accessToken = "access-token",
            refreshToken = "refresh-token"
        )
        coEvery { userRepository.loginDeviceByPassword(givenLoginByPasswordDomainModel) } returns expectedResponse
        coEvery { authTokenService.storeTokens(any()) } returns Unit
        every { accessTokenApiToAuthSessionDomainModelMapper.map(expectedResponse) } returns expectedDomainModel

        //When
        service.loginDeviceByPassword(givenLoginByPasswordDomainModel)

        //Then
        coVerify { userRepository.loginDeviceByPassword(givenLoginByPasswordDomainModel) }
        verify { accessTokenApiToAuthSessionDomainModelMapper.map(expectedResponse) }
    }
}
