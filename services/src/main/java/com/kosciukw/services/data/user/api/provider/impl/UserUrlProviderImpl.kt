package com.kosciukw.services.data.user.api.provider.impl

import com.kosciukw.services.data.user.api.provider.UserUrlProvider

class UserUrlProviderImpl : UserUrlProvider {

    override fun getPairByPasswordUrl() = PAIR_BY_PASSWORD_URL

    companion object {
        private const val PAIR_BY_PASSWORD_URL = "/auth/login"
    }
}