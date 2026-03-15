package com.kosciukw.services.internal.user.api.controller.impl

import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.api.controller.UserApiController
import com.kosciukw.services.internal.user.api.provider.UserUrlProvider
import com.kosciukw.services.internal.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.internal.user.model.api.request.RefreshRequest
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest

class UserApiControllerImpl(
    private val userApi: UserApi,
    private val userUrlProvider: UserUrlProvider,
    private val userExceptionMapper: UserExceptionMapper
) : UserApiController {

    override suspend fun loginByPassword(request: LoginByPasswordRequest) =
        userExceptionMapper.mapException {
            userApi.loginByPassword(
                loginByPasswordRequest = request,
                url = userUrlProvider.getLoginByPasswordUrl()
            )
        }

    override suspend fun startOtpRegistrationRequest(request: StartOtpRegistrationRequest) {
        userExceptionMapper.mapException {
            userApi.startOtpRegistration(
                startOtpRegistrationRequest = request,
                url = userUrlProvider.getStartOtpRegistrationUrl()
            )
        }
    }

    override suspend fun finalizeOtpRegistrationRequest(request: FinalizeOtpRegistrationRequest) =
        userExceptionMapper.mapException {
            userApi.finalizeOtpRegistration(
                finalizeOtpRegistrationRequest = request,
                url = userUrlProvider.getFinalizeOtpRegistrationUrl()
            )
        }

    override suspend fun refreshToken(request: RefreshRequest) =
        userExceptionMapper.mapException {
            userApi.refreshToken(
                refreshRequest = request,
                url = userUrlProvider.getRefreshTokenUrl()
            )
        }
}