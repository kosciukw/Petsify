package com.kosciukw.services.data.user.mapper.impl

import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.model.api.request.SignUpRequest
import com.kosciukw.services.data.user.model.domain.SignUpDomainModel

class SignUpDomainToRequestModelMapperImpl : SignUpDomainToRequestModelMapper {

    override fun map(input: SignUpDomainModel) = with(input) {
        SignUpRequest(
            email = email,
            password = password,
            name = name,
            termsAccepted = termsAccepted,
            marketingAccepted = marketingAccepted
        )
    }
}