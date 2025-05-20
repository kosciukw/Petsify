package com.kosciukw.services.data.user.mapper

import com.kosciukw.services.data.user.model.api.request.PairByPasswordRequest
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import pl.kosciukw.petsify.shared.mapper.ModelMapper

interface PairByPasswordDomainToRequestModelMapper :
    ModelMapper<PairByPasswordDomainModel, PairByPasswordRequest>