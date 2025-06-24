package com.kosciukw.services.data.user.mapper.impl

import com.kosciukw.services.data.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel

class StartOtpRegistrationDomainToRequestModelMapperImpl :
    StartOtpRegistrationDomainToRequestModelMapper {

    override fun map(input: StartOtpRegistrationDomainModel) = with(input) {
        StartOtpRegistrationRequest(
            email = email
        )
    }
}