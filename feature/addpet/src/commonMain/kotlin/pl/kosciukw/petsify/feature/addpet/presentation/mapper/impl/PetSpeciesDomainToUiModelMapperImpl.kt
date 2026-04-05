package pl.kosciukw.petsify.feature.addpet.presentation.mapper.impl

import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetSpeciesDomainToUiModelMapper

class PetSpeciesDomainToUiModelMapperImpl : PetSpeciesDomainToUiModelMapper {

    override fun map(input: PetSpeciesDomainType) = when (input) {
        PetSpeciesDomainType.Dog -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = true
        )

        PetSpeciesDomainType.Cat -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = true
        )

        PetSpeciesDomainType.Rabbit -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false
        )

        PetSpeciesDomainType.Hamster -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false
        )

        PetSpeciesDomainType.Bird -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false
        )

        PetSpeciesDomainType.Fish -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false
        )

        PetSpeciesDomainType.Turtle -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false
        )

        PetSpeciesDomainType.Other -> PetSpeciesUiModel(
            petSpeciesDomainType = input,
            isPrimary = false,
            requiresCustomValue = true
        )
    }
}
