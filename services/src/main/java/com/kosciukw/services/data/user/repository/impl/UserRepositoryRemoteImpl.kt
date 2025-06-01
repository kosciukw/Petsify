package com.kosciukw.services.data.user.repository.impl

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import pl.kosciukw.petsify.shared.callback.mapResult
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.suspendNetworkRequest
import javax.inject.Inject

class UserRepositoryRemoteImpl @Inject constructor(
    private val pairByPasswordDomainToRequestModelMapper: PairByPasswordDomainToRequestModelMapper,
    private val signUpDomainToRequestModelMapper: SignUpDomainToRequestModelMapper,
    private val networkStateProvider: NetworkStateProvider,
    private val errorMapper: UserApiToDomainErrorMapper,
    private val userApiController: UserApiController
) : UserRepository {

    override suspend fun pairDeviceByPassword(
        pairByPasswordDomainModel: PairByPasswordDomainModel
    ) = suspendNetworkRequest(networkStateProvider) {
        mapResult(errorMapper = errorMapper) {
            userApiController.pairByPassword(
                request = pairByPasswordDomainToRequestModelMapper.map(pairByPasswordDomainModel)
            )
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