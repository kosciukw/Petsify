package pl.kosciukw.petsify.feature.addpet.presentation.mapper

import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel

interface PetSpeciesDomainToUiModelMapper {
    fun map(input: PetSpeciesDomainType): PetSpeciesUiModel
}
