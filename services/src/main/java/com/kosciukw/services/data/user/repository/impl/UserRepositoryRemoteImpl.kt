package com.kosciukw.services.data.user.repository.impl

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.model.api.request.RefreshRequest
import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.RefreshTokenDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel
import pl.kosciukw.petsify.shared.callback.mapResult
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.suspendNetworkRequest
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class UserRepositoryRemoteImpl @Inject constructor(
    private val loginByPasswordDomainToRequestModelMapper: LoginByPasswordDomainToRequestModelMapper,
    private val signUpDomainToRequestModelMapper: SignUpDomainToRequestModelMapper,
    private val startOtpRegistrationDomainToRequestModelMapper: StartOtpRegistrationDomainToRequestModelMapper,
    private val finalizeOtpRegistrationDomainToRequestModelMapper: FinalizeOtpRegistrationDomainToRequestModelMapper,
    private val networkStateProvider: NetworkStateProvider,
    private val errorMapper: UserApiToDomainErrorMapper,
    private val userApiController: UserApiController,
//    private val authSessionRepository: AuthSessionRepository
) : UserRepository {

    override suspend fun loginDeviceByPassword(
        loginByPasswordDomainModel: LoginByPasswordDomainModel
    ) = suspendNetworkRequest(networkStateProvider) {
        mapResult(errorMapper = errorMapper) {
            userApiController.loginByPassword(
                request = loginByPasswordDomainToRequestModelMapper.map(loginByPasswordDomainModel)
            )
        }
    }
//        .also { response ->
//        authSessionRepository.saveTokens(
//            AuthTokens(
//                accessToken = response.accessToken,
//                refreshToken = response.refreshToken ?: String.empty()
//            )
//        )
//    }

    override suspend fun finalizeOtpRegistration(finalizeOtpRegistrationDomainModel: FinalizeOtpRegistrationDomainModel) =
        suspendNetworkRequest(networkStateProvider) {
            mapResult(errorMapper = errorMapper) {
                userApiController.finalizeOtpRegistrationRequest(
                    request = finalizeOtpRegistrationDomainToRequestModelMapper.map(
                        finalizeOtpRegistrationDomainModel
                    )
                )
            }
        }
//            .also { response ->
//            authSessionRepository.saveTokens(
//                AuthTokens(
//                    accessToken = response.accessToken,
//                    refreshToken = response.refreshToken ?: String.empty()
//                )
//            )
//        }

    override suspend fun refreshToken(refreshTokenDomainModel: RefreshTokenDomainModel) =
        suspendNetworkRequest(networkStateProvider) {
            mapResult(errorMapper = errorMapper) {
                userApiController.refreshToken(
                    request = RefreshRequest(
                        refreshToken = refreshTokenDomainModel.refreshToken
                    )
                )
            }
        }

    override suspend fun startOtpRegistration(startOtpRegistrationDomainModel: StartOtpRegistrationDomainModel) {
        suspendNetworkRequest(networkStateProvider) {
            mapResult(errorMapper = errorMapper) {
                userApiController.startOtpRegistrationRequest(
                    request = startOtpRegistrationDomainToRequestModelMapper.map(
                        startOtpRegistrationDomainModel
                    )
                )
            }
        }
    }

    @Deprecated("Use Start + FinalizeRegistration")
    override suspend fun signUp(signUpDomainModel: SignUpDomainModel) {
        suspendNetworkRequest(networkStateProvider) {
            mapResult(errorMapper = errorMapper) {
                userApiController.signUp(
                    request = signUpDomainToRequestModelMapper.map(signUpDomainModel)
                )
            }
        }
    }
}