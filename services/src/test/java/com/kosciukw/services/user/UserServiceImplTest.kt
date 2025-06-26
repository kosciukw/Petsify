package com.kosciukw.services.user

import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.service.user.UserService
import com.kosciukw.services.data.user.service.user.impl.UserServiceImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserServiceImplTest {

    private lateinit var service: UserService

    private val userRepository: UserRepository = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        service = UserServiceImpl(
            userRepository = userRepository
        )
    }

    @Test
    fun `When login device called Then should call proper method in repository`() = runTest {
        //Given
        val givenLoginByPasswordDomainModel = LoginByPasswordDomainModel(
            email = "email",
            password = "password"
        )

        //When
        userRepository.loginDeviceByPassword(givenLoginByPasswordDomainModel)

        //Then
        coVerify { userRepository.loginDeviceByPassword(givenLoginByPasswordDomainModel) }
    }
}