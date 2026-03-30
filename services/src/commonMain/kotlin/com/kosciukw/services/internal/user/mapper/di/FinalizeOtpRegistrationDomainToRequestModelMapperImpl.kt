package com.kosciukw.services.internal.user.mapper.di

import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.internal.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest

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
