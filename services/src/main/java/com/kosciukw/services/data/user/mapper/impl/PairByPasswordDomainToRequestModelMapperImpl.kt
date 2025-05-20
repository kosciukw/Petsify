package com.kosciukw.services.data.user.mapper.impl

import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel

class PairByPasswordDomainToRequestModelMapperImpl : PairByPasswordDomainToRequestModelMapper {

    override fun map(input: PairByPasswordDomainModel) = PairByPasswordRequest(
        password = input.password,
        email = input.email
    )
}