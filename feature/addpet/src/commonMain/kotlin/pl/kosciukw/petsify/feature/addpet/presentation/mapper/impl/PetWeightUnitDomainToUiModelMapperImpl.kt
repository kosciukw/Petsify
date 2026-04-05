package pl.kosciukw.petsify.feature.addpet.presentation.mapper.impl

import pl.kosciukw.petsify.feature.addpet.model.domain.PetWeightUnitDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetWeightUnitUiModel
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetWeightUnitDomainToUiModelMapper

class PetWeightUnitDomainToUiModelMapperImpl : PetWeightUnitDomainToUiModelMapper {

    override fun map(input: PetWeightUnitDomainType) = PetWeightUnitUiModel(
        petWeightUnitDomainType = input
    )
}
