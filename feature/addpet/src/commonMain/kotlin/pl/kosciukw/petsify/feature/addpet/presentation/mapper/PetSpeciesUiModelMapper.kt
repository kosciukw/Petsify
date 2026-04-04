package pl.kosciukw.petsify.feature.addpet.presentation.mapper

import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel

fun PetSpeciesDomainType.toUiModel(): PetSpeciesUiModel = when (this) {
    PetSpeciesDomainType.Dog -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = true
    )

    PetSpeciesDomainType.Cat -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = true
    )

    PetSpeciesDomainType.Rabbit -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false
    )

    PetSpeciesDomainType.Hamster -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false
    )

    PetSpeciesDomainType.Bird -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false
    )

    PetSpeciesDomainType.Fish -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false
    )

    PetSpeciesDomainType.Turtle -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false
    )

    PetSpeciesDomainType.Other -> PetSpeciesUiModel(
        petSpeciesDomainType = this,
        isPrimary = false,
        requiresCustomValue = true
    )
}
