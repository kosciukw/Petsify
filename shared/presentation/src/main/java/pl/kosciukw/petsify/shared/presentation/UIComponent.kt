package pl.kosciukw.petsify.shared.presentation

sealed class UIComponent {
    data class ToastSimple(val title: String) : UIComponent()
    data class Dialog(val title: String, val description: String) : UIComponent()
    data class None(val message: String) : UIComponent()
}