package pl.kosciukw.petsify.feature.addpet.presentation.ui

import pl.kosciukw.petsify.feature.addpet.presentation.AddPetAction
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetEvent
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetState
import pl.kosciukw.petsify.feature.addpet.presentation.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.presentation.PetSpeciesUiModelProvider
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.presentation.common.viewmodel.BaseViewModel

class AddPetViewModel(
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<AddPetEvent, AddPetState, AddPetAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState(): AddPetState {
        val speciesOptions = PetSpeciesUiModelProvider().getSpecies()
        return AddPetState(
            speciesOptions = speciesOptions,
            selectedSpecies = speciesOptions.firstOrNull { it.code == PetSpeciesUiModelProvider.DOG_CODE }
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
                        customSpecies = if (event.value.requiresCustomValue) customSpecies else "",
                        isOtherSpeciesExpanded = if (event.value.isPrimary) false else isOtherSpeciesExpanded
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
                        age = if (event.value) "" else age,
                        birthDate = if (event.value) birthDate else ""
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
        else -> selectedSpecies?.code.orEmpty()
    }

    private fun AddPetState.currentAgeOrBirthDateValue(): String = if (knowsBirthDate) {
        birthDate
    } else {
        age
    }
}
