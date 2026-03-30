package com.kosciukw.services.api.session

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.session.model.RefreshTokenDomainModel

interface SessionService {

    suspend fun refreshToken(
        request: RefreshTokenDomainModel
    ): AuthSessionDomainModel

    suspend fun isSignedIn(): Boolean
}
