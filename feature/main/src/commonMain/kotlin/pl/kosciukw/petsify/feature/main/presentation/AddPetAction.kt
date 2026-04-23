package pl.kosciukw.petsify.feature.main.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewSingleAction

sealed class AddPetAction : ViewSingleAction {
    data object NavigateUp : AddPetAction()
}
