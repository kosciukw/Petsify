package com.kosciukw.services.internal.user.mapper

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.internal.user.model.api.response.AccessTokenApiModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface AccessTokenApiToAuthSessionDomainModelMapper :
    ModelMapper<AccessTokenApiModel, AuthSessionDomainModel>
