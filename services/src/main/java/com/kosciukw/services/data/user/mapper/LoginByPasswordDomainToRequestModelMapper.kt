package com.kosciukw.services.data.user.mapper

import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface LoginByPasswordDomainToRequestModelMapper :
    ModelMapper<LoginByPasswordDomainModel, LoginByPasswordRequest>