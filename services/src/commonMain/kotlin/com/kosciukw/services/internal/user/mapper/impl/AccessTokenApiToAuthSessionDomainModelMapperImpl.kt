package com.kosciukw.services.internal.user.mapper.impl

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.internal.user.mapper.AccessTokenApiToAuthSessionDomainModelMapper
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel
import pl.kosciukw.petsify.shared.utils.empty

class AccessTokenApiToAuthSessionDomainModelMapperImpl :
    AccessTokenApiToAuthSessionDomainModelMapper {

    override fun map(input: AccessTokenApiModel) = AuthSessionDomainModel(
        accessToken = input.accessToken,
        refreshToken = input.refreshToken ?: String.empty()
    )
}
