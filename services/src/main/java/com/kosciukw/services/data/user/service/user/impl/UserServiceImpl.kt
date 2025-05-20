package com.kosciukw.services.data.user.service.user.impl

import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.service.user.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun pairDeviceByPassword(
        request: PairByPasswordDomainModel
    ) = userRepository.pairDeviceByPassword(request)
}