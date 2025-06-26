package com.kosciukw.services.data.user.mapper

import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface SignUpDomainToRequestModelMapper :
    ModelMapper<SignUpDomainModel, SignUpRequest>