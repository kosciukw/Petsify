package com.kosciukw.services.internal.user.mapper.impl

import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel
import com.kosciukw.services.internal.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest

class StartOtpRegistrationDomainToRequestModelMapperImpl :
    StartOtpRegistrationDomainToRequestModelMapper {

    override fun map(input: StartOtpRegistrationDomainModel) = with(input) {
        StartOtpRegistrationRequest(
            email = email
        )
    }
}
