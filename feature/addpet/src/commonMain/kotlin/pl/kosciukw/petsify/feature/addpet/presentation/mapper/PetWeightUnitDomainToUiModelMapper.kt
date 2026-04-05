package pl.kosciukw.petsify.feature.addpet.presentation.mapper

import pl.kosciukw.petsify.feature.addpet.model.domain.PetWeightUnitDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetWeightUnitUiModel

interface PetWeightUnitDomainToUiModelMapper {
    fun map(input: PetWeightUnitDomainType): PetWeightUnitUiModel
}
