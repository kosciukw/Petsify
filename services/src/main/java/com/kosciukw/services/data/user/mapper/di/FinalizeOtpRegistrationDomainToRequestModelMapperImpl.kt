package com.kosciukw.services.data.user.mapper.di

import com.kosciukw.services.data.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel

class FinalizeOtpRegistrationDomainToRequestModelMapperImpl :
    FinalizeOtpRegistrationDomainToRequestModelMapper {

    override fun map(input: FinalizeOtpRegistrationDomainModel) = with(input) {
        FinalizeOtpRegistrationRequest(
            email = email,
            password = password,
            name = name,
            termsAccepted = termsAccepted,
            marketingAccepted = marketingAccepted,
            otp = otp
        )
    }
}