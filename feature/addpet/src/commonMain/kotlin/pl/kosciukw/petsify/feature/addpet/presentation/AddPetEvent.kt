package pl.kosciukw.petsify.feature.addpet.presentation

import pl.kosciukw.petsify.feature.addpet.model.ui.PetSexUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetTemperamentUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetWeightUnitUiModel
import pl.kosciukw.petsify.shared.presentation.components.view.ViewEvent

sealed class AddPetEvent : ViewEvent {
    data class OnNameChanged(val value: String) : AddPetEvent()
    data class OnSpeciesSelected(val value: PetSpeciesUiModel) : AddPetEvent()
    data object OnOtherSpeciesToggled : AddPetEvent()
    data class OnCustomSpeciesChanged(val value: String) : AddPetEvent()
    data class OnKnowsBirthDateChanged(val value: Boolean) : AddPetEvent()
    data class OnAgeChanged(val value: String) : AddPetEvent()
    data class OnBirthDateChanged(val value: String) : AddPetEvent()
    data class OnWeightChanged(val value: String) : AddPetEvent()
    data class OnWeightUnitSelected(val value: PetWeightUnitUiModel) : AddPetEvent()
    data object OnMoreDetailsToggled : AddPetEvent()
    data class OnBreedChanged(val value: String) : AddPetEvent()
    data class OnSexSelected(val value: PetSexUiModel) : AddPetEvent()
    data class OnTemperamentToggled(val value: PetTemperamentUiModel) : AddPetEvent()
    data class OnColorChanged(val value: String) : AddPetEvent()
    data class OnNotesChanged(val value: String) : AddPetEvent()
    data object OnSaveClicked : AddPetEvent()
}
