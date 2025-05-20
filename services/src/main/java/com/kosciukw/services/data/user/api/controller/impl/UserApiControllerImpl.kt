package com.kosciukw.services.data.user.api.controller.impl

import com.kosciukw.services.data.user.api.UserApi
import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.api.provider.UserUrlProvider
import com.kosciukw.services.data.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest

class UserApiControllerImpl(
    private val userApi: UserApi,
    private val userUrlProvider: UserUrlProvider,
    private val userExceptionMapper: UserExceptionMapper
) : UserApiController {

    override suspend fun pairByPassword(request: PairByPasswordRequest) =
        userExceptionMapper.mapException {
            userApi.pairByPassword(
                pairByPasswordRequest = request,
                url = userUrlProvider.getPairByPasswordUrl()
            )
        }
}