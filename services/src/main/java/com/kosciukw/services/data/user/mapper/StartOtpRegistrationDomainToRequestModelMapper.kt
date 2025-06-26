package com.kosciukw.services.data.user.mapper

import com.kosciukw.services.data.user.model.api.request.StartOtpRegistrationRequest
import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface StartOtpRegistrationDomainToRequestModelMapper :
    ModelMapper<StartOtpRegistrationDomainModel, StartOtpRegistrationRequest>