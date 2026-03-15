package com.kosciukw.services.internal.user.mapper

import com.kosciukw.services.api.registration.model.StartOtpRegistrationDomainModel
import com.kosciukw.services.internal.user.model.api.request.StartOtpRegistrationRequest
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface StartOtpRegistrationDomainToRequestModelMapper :
    ModelMapper<StartOtpRegistrationDomainModel, StartOtpRegistrationRequest>
