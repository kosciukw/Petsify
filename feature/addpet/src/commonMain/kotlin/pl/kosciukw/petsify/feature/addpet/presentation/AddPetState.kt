package pl.kosciukw.petsify.feature.addpet.presentation

import pl.kosciukw.petsify.feature.addpet.model.ui.PetSexUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetTemperamentUiModel
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState

data class AddPetState(
    val photoPath: String? = null,
    val name: String = "",
    val speciesOptions: List<PetSpeciesUiModel> = emptyList(),
    val selectedSpecies: PetSpeciesUiModel? = null,
    val isOtherSpeciesExpanded: Boolean = false,
    val customSpecies: String = "",
    val knowsBirthDate: Boolean = true,
    val age: String = "",
    val birthDate: String = "",
    val weight: String = "",
    val isMoreDetailsExpanded: Boolean = false,
    val breed: String = "",
    val sex: PetSexUiModel? = null,
    val temperaments: Set<PetTemperamentUiModel> = emptySet(),
    val color: String = "",
    val notes: String = "",
    val isSaveEnabled: Boolean = false
) : ViewState
