package com.kosciukw.services.data.user.mapper

import com.kosciukw.services.data.user.model.api.request.FinalizeOtpRegistrationRequest
import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface FinalizeOtpRegistrationDomainToRequestModelMapper :
    ModelMapper<FinalizeOtpRegistrationDomainModel, FinalizeOtpRegistrationRequest>