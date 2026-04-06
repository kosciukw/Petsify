package pl.kosciukw.petsify.feature.addpet.model.ui

import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType

data class PetSpeciesUiModel(
    val petSpeciesDomainType: PetSpeciesDomainType,
    val isPrimary: Boolean,
    val requiresCustomValue: Boolean = false
)
