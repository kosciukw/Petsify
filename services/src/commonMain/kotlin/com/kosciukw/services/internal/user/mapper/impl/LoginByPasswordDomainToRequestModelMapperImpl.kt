package com.kosciukw.services.internal.user.mapper.impl

import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import com.kosciukw.services.internal.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.internal.user.model.api.request.LoginByPasswordRequest

class LoginByPasswordDomainToRequestModelMapperImpl : LoginByPasswordDomainToRequestModelMapper {

    override fun map(input: LoginByPasswordDomainModel) = LoginByPasswordRequest(
        password = input.password,
        email = input.email
    )
}
