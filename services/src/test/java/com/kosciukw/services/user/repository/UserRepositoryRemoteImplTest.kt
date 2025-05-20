package com.kosciukw.services.user.repository

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
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
    private val networkStateProvider: NetworkStateProvider = mockk(relaxed = true)
    private val errorMapper: UserApiToDomainErrorMapper = mockk(relaxed = true)
    private val pairByPasswordDomainToRequestModelMapper: PairByPasswordDomainToRequestModelMapper =
        mockk(relaxed = true)
    private val userApiController: UserApiController = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        repository = UserRepositoryRemoteImpl(
            networkStateProvider = networkStateProvider,
            errorMapper = errorMapper,
            userApiController = userApiController,
            pairByPasswordDomainToRequestModelMapper = pairByPasswordDomainToRequestModelMapper
        )
    }

    @Test
    fun `When pair device called Then should call proper method in controller`() {
        //Given
        val givenPairByPasswordDomainModel = PairByPasswordDomainModel(
            email = "email",
            password = "password"
        )

        val givenPairDeviceByPasswordRequest = PairByPasswordRequest(
            email = "email",
            password = "password"
        )

        every {
            networkStateProvider.isInternetConnectionAvailable()
        } returns true
        every {
            pairByPasswordDomainToRequestModelMapper.map(givenPairByPasswordDomainModel)
        } returns givenPairDeviceByPasswordRequest

        //When
        runBlocking {
            repository.pairDeviceByPassword(givenPairByPasswordDomainModel)
        }

        //Then
        coVerify { userApiController.pairByPassword(givenPairDeviceByPasswordRequest) }
    }

    @Test
    fun `When pair device called And no internet connection Then should throw proper exception`() {
        // Given
        val givenPairByPasswordDomainModel = PairByPasswordDomainModel(
            email = "email",
            password = "password"
        )

        every { networkStateProvider.isInternetConnectionAvailable() } returns false

        // When & Then
        assertThrows<CoreDomainError.NoInternetConnection> {
            runBlocking {
                repository.pairDeviceByPassword(givenPairByPasswordDomainModel)
            }
        }

        coVerify(exactly = 0) { userApiController.pairByPassword(any()) }
    }
}