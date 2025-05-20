package com.kosciukw.services.data.user.repository.impl

import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import pl.kosciukw.petsify.shared.callback.mapResult
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.suspendNetworkRequest
import javax.inject.Inject

class UserRepositoryRemoteImpl @Inject constructor(
    private val pairByPasswordDomainToRequestModelMapper: PairByPasswordDomainToRequestModelMapper,
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
}