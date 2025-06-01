package com.kosciukw.services.data.user.api.provider.impl

import com.kosciukw.services.data.user.api.provider.UserUrlProvider

class UserUrlProviderImpl : UserUrlProvider {

    override fun getPairByPasswordUrl() = PAIR_BY_PASSWORD_URL

    override fun getSignUpUrl() = SIGN_UP_URL

    companion object {
        private const val PAIR_BY_PASSWORD_URL = "/auth/login"
        private const val SIGN_UP_URL = "/auth/register"
    }
}