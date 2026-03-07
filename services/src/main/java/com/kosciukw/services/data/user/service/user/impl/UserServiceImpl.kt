package com.kosciukw.services.data.user.service.user.impl

import com.kosciukw.services.data.session.model.AuthTokens
import com.kosciukw.services.data.session.service.AuthTokenService
import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.RefreshTokenDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.service.user.UserService
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val authTokenService: AuthTokenService
) : UserService {

    override suspend fun loginDeviceByPassword(
        request: LoginByPasswordDomainModel
    ) = userRepository.loginDeviceByPassword(request)
        .also { response ->
            authTokenService.storeTokens(
                tokens = AuthTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken ?: String.empty()
                )
            )
        }

    override suspend fun startOtpRegistration(
        request: StartOtpRegistrationDomainModel
    ) {
        userRepository.startOtpRegistration(request)
    }

    override suspend fun finalizeOtpRegistration(
        request: FinalizeOtpRegistrationDomainModel
    ) = userRepository.finalizeOtpRegistration(request).also { response ->
        authTokenService.storeTokens(
            tokens = AuthTokens(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken ?: String.empty()
            )
        )
    }

    override suspend fun refreshToken(
        request: RefreshTokenDomainModel
    ) = userRepository.refreshToken(request)

    override suspend fun signUp(
        request: SignUpDomainModel
    ) {
        userRepository.signUp(request)
    }

    override suspend fun isSignedIn() =
        authTokenService.getAccessToken() != null //todo check if correct
}