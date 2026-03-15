package com.kosciukw.services.internal.user.repository.impl

import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel
import com.kosciukw.services.api.session.model.RefreshTokenDomainModel
import com.kosciukw.services.internal.user.api.controller.UserApiController
import com.kosciukw.services.internal.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import com.kosciukw.services.internal.user.repository.UserRepository
import pl.kosciukw.petsify.shared.callback.mapResult
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.suspendNetworkRequest
import javax.inject.Inject

class UserRepositoryRemoteImpl @Inject constructor(
    private val loginByPasswordDomainToRequestModelMapper: LoginByPasswordDomainToRequestModelMapper,
    private val startOtpRegistrationDomainToRequestModelMapper: StartOtpRegistrationDomainToRequestModelMapper,
    private val finalizeOtpRegistrationDomainToRequestModelMapper: FinalizeOtpRegistrationDomainToRequestModelMapper,
    private val networkStateProvider: NetworkStateProvider,
    private val errorMapper: UserApiToDomainErrorMapper,
    private val userApiController: UserApiController
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
}
