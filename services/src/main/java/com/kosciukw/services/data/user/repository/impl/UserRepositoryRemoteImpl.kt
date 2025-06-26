package com.kosciukw.services.data.user.repository.impl

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel
import pl.kosciukw.petsify.shared.callback.mapResult
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.suspendNetworkRequest
import javax.inject.Inject

class UserRepositoryRemoteImpl @Inject constructor(
    private val loginByPasswordDomainToRequestModelMapper: LoginByPasswordDomainToRequestModelMapper,
    private val signUpDomainToRequestModelMapper: SignUpDomainToRequestModelMapper,
    private val startOtpRegistrationDomainToRequestModelMapper: StartOtpRegistrationDomainToRequestModelMapper,
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

    override suspend fun startOtpRegistration(startOtpRegistrationDomainModel: StartOtpRegistrationDomainModel) {
        suspendNetworkRequest(networkStateProvider) {
            mapResult(errorMapper = errorMapper) {
                userApiController.startOtpRegistrationRequest(
                    request = startOtpRegistrationDomainToRequestModelMapper.map(startOtpRegistrationDomainModel)
                )
            }
        }
    }

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