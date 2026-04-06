package pl.kosciukw.petsify.feature.addpet.presentation.ui

import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType
import pl.kosciukw.petsify.feature.addpet.model.domain.PetWeightUnitDomainType
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetAction
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetEvent
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetState
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetSpeciesDomainToUiModelMapper
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetWeightUnitDomainToUiModelMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.presentation.common.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.utils.empty
import kotlin.enums.enumEntries

class AddPetViewModel(
    integrationErrorMapper: IntegrationErrorMapper,
    private val petSpeciesDomainToUiModelMapper: PetSpeciesDomainToUiModelMapper,
    private val petWeightUnitDomainToUiModelMapper: PetWeightUnitDomainToUiModelMapper
) : BaseViewModel<AddPetEvent, AddPetState, AddPetAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState(): AddPetState {

        val speciesOptions = enumEntries<PetSpeciesDomainType>()
            .map(petSpeciesDomainToUiModelMapper::map)

        val weightUnits = enumEntries<PetWeightUnitDomainType>()
            .map(petWeightUnitDomainToUiModelMapper::map)

        return AddPetState(
            speciesOptions = speciesOptions,
            selectedSpecies = speciesOptions.firstOrNull { it.petSpeciesDomainType == PetSpeciesDomainType.Dog },
            weightUnits = weightUnits,
            selectedWeightUnit = weightUnits.firstOrNull {
                it.petWeightUnitDomainType == PetWeightUnitDomainType.Kilograms
            }
        ).withSaveState()
    }

    override fun onTriggerEvent(event: AddPetEvent) {
        when (event) {
            is AddPetEvent.OnNameChanged -> {
                setState { copy(name = event.value) }
                updateSaveState()
            }

            is AddPetEvent.OnSpeciesSelected -> {
                setState {
                    copy(
                        selectedSpecies = event.value,
                        customSpecies = if (event.value.requiresCustomValue) customSpecies
                        else String.empty(),
                        isOtherSpeciesExpanded = if (event.value.isPrimary) false
                        else isOtherSpeciesExpanded
                    )
                }
                updateSaveState()
            }

            AddPetEvent.OnOtherSpeciesToggled -> {
                setState { copy(isOtherSpeciesExpanded = !isOtherSpeciesExpanded) }
            }

            is AddPetEvent.OnCustomSpeciesChanged -> {
                setState { copy(customSpecies = event.value) }
                updateSaveState()
            }

            is AddPetEvent.OnKnowsBirthDateChanged -> {
                setState {
                    copy(
                        knowsBirthDate = event.value,
                        age = if (event.value) String.empty() else age,
                        birthDate = if (event.value) birthDate else String.empty()
                    )
                }
                updateSaveState()
            }

            is AddPetEvent.OnAgeChanged -> {
                setState { copy(age = event.value) }
                updateSaveState()
            }

            is AddPetEvent.OnBirthDateChanged -> {
                setState { copy(birthDate = event.value) }
                updateSaveState()
            }

            is AddPetEvent.OnWeightChanged -> {
                setState { copy(weight = event.value) }
                updateSaveState()
            }

            is AddPetEvent.OnWeightUnitSelected -> {
                setState { copy(selectedWeightUnit = event.value) }
            }

            AddPetEvent.OnMoreDetailsToggled -> {
                setState { copy(isMoreDetailsExpanded = !isMoreDetailsExpanded) }
            }

            is AddPetEvent.OnBreedChanged -> {
                setState { copy(breed = event.value) }
            }

            is AddPetEvent.OnSexSelected -> {
                setState { copy(sex = event.value) }
            }

            is AddPetEvent.OnTemperamentToggled -> {
                setState {
                    copy(
                        temperaments = temperaments.toMutableSet().apply {
                            if (!add(event.value)) remove(event.value)
                        }
                    )
                }
            }

            is AddPetEvent.OnColorChanged -> {
                setState { copy(color = event.value) }
            }

            is AddPetEvent.OnNotesChanged -> {
                setState { copy(notes = event.value) }
            }

            AddPetEvent.OnSaveClicked -> {
                if (state.value.isSaveEnabled) {
                    setAction { AddPetAction.NavigateUp }
                }
            }
        }
    }

    private fun updateSaveState() {
        setState { withSaveState() }
    }

    private fun AddPetState.withSaveState(): AddPetState = copy(
        isSaveEnabled = name.isNotBlank() &&
                currentSpeciesValue().isNotBlank() &&
                currentAgeOrBirthDateValue().isNotBlank() &&
                weight.isNotBlank()
    )

    private fun AddPetState.currentSpeciesValue(): String = when {
        selectedSpecies?.requiresCustomValue == true -> customSpecies
        else -> selectedSpecies?.petSpeciesDomainType?.name.orEmpty()
    }

    private fun AddPetState.currentAgeOrBirthDateValue(): String = if (knowsBirthDate) {
        birthDate
    } else {
        age
    }
}
