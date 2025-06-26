package com.kosciukw.services.data.user.mapper.impl

import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.model.api.request.LoginByPasswordRequest
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel

class LoginByPasswordDomainToRequestModelMapperImpl : LoginByPasswordDomainToRequestModelMapper {

    override fun map(input: LoginByPasswordDomainModel) = LoginByPasswordRequest(
        password = input.password,
        email = input.email
    )
}