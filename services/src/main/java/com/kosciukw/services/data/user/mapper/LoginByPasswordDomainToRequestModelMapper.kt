package com.kosciukw.services.internal.user.mapper

import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface LoginByPasswordDomainToRequestModelMapper :
    ModelMapper<LoginByPasswordDomainModel, LoginByPasswordRequest>
