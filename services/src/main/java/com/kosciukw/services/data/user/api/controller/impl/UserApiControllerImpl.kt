package com.kosciukw.services.data.user.api.controller.impl

import com.kosciukw.services.data.user.api.UserApi
import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.api.provider.UserUrlProvider
import com.kosciukw.services.data.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest

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

    override suspend fun signUp(request: SignUpRequest) {
        userExceptionMapper.mapException {
            userApi.signUp(
                signUpRequest = request,
                url = userUrlProvider.getLoginByPasswordUrl()
            )
        }
    }
}