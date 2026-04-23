package com.kosciukw.services.api.auth

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel

interface AuthService {

    suspend fun loginDeviceByPassword(
        request: LoginByPasswordDomainModel
    ): AuthSessionDomainModel
}
