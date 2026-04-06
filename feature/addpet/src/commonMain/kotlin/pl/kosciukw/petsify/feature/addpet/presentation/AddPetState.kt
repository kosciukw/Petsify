package pl.kosciukw.petsify.feature.addpet.presentation

import pl.kosciukw.petsify.feature.addpet.model.ui.PetSexUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetTemperamentUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetWeightUnitUiModel
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState
import pl.kosciukw.petsify.shared.utils.empty

data class AddPetState(
    val photoPath: String? = null,
    val name: String = String.empty(),
    val speciesOptions: List<PetSpeciesUiModel> = emptyList(),
    val selectedSpecies: PetSpeciesUiModel? = null,
    val isOtherSpeciesExpanded: Boolean = false,
    val customSpecies: String = String.empty(),
    val knowsBirthDate: Boolean = true,
    val age: String = String.empty(),
    val birthDate: String = String.empty(),
    val weight: String = String.empty(),
    val weightUnits: List<PetWeightUnitUiModel> = emptyList(),
    val selectedWeightUnit: PetWeightUnitUiModel? = null,
    val isMoreDetailsExpanded: Boolean = false,
    val breed: String = String.empty(),
    val sex: PetSexUiModel? = null,
    val temperaments: Set<PetTemperamentUiModel> = emptySet(),
    val color: String = String.empty(),
    val notes: String = String.empty(),
    val isSaveEnabled: Boolean = false
) : ViewState
