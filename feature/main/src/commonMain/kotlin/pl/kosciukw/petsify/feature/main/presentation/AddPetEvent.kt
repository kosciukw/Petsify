package pl.kosciukw.petsify.feature.main.presentation

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
    data object OnMoreDetailsToggled : AddPetEvent()
    data class OnBreedChanged(val value: String) : AddPetEvent()
    data class OnSexSelected(val value: PetSexUiModel) : AddPetEvent()
    data class OnTemperamentToggled(val value: PetTemperamentUiModel) : AddPetEvent()
    data class OnColorChanged(val value: String) : AddPetEvent()
    data class OnNotesChanged(val value: String) : AddPetEvent()
    data object OnSaveClicked : AddPetEvent()
}

enum class PetSexUiModel {
    Male,
    Female
}

enum class PetTemperamentUiModel {
    Calm,
    Energetic,
    Friendly,
    Shy,
    Curious
}
