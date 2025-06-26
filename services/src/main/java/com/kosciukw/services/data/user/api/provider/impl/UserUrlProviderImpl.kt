package com.kosciukw.services.data.user.api.provider.impl

import com.kosciukw.services.data.user.api.provider.UserUrlProvider

class UserUrlProviderImpl : UserUrlProvider {

    override fun getLoginByPasswordUrl() = LOGIN_BY_PASSWORD_URL
    override fun getStartOtpRegistrationUrl() = START_OTP_REGISTRATION
    override fun getSignUpUrl() = SIGN_UP_URL

    companion object {
        private const val LOGIN_BY_PASSWORD_URL = "/auth/login"
        private const val START_OTP_REGISTRATION = "/auth/start-otp-registration"
        private const val SIGN_UP_URL = "/auth/register"
    }
}