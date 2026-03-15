package com.kosciukw.services.internal.user.mapper

import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.internal.user.model.api.request.FinalizeOtpRegistrationRequest
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface FinalizeOtpRegistrationDomainToRequestModelMapper :
    ModelMapper<FinalizeOtpRegistrationDomainModel, FinalizeOtpRegistrationRequest>
