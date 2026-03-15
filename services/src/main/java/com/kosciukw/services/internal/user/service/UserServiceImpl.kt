package com.kosciukw.services.internal.user.service

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.api.registration.RegistrationService
import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel
import com.kosciukw.services.api.session.SessionService
import com.kosciukw.services.api.session.model.RefreshTokenDomainModel
import com.kosciukw.services.internal.session.model.AuthTokens
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.user.mapper.AccessTokenApiToAuthSessionDomainModelMapper
import com.kosciukw.services.internal.user.repository.UserRepository
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val authTokenService: AuthTokenService,
    private val accessTokenApiToAuthSessionDomainModelMapper: AccessTokenApiToAuthSessionDomainModelMapper
) : AuthService, RegistrationService, SessionService {

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
        .let(accessTokenApiToAuthSessionDomainModelMapper::map)

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
        .let(accessTokenApiToAuthSessionDomainModelMapper::map)

    override suspend fun refreshToken(
        request: RefreshTokenDomainModel
    ) = userRepository.refreshToken(request)
        .let(accessTokenApiToAuthSessionDomainModelMapper::map)

    override suspend fun isSignedIn() =
        authTokenService.getAccessToken() != null //todo check if correct
}
