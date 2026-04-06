package pl.kosciukw.petsify.feature.addpet.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction

sealed class AddPetAction : ViewSingleAction {
    data object NavigateUp : AddPetAction()
}
