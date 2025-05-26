package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class SignUpEvent : ViewEvent {
    data class OnNameTextChanged(val value: String) : SignUpEvent()
    data class OnEmailTextChanged(val value: String) : SignUpEvent()
    data class OnPasswordTextChanged(val value: String) : SignUpEvent()
    data class OnConfirmPasswordTextChanged(val value: String) : SignUpEvent()
    data class OnTermsAcceptedChanged(val accepted: Boolean) : SignUpEvent()
//    data class Register(
//        val name: String,
//        val email: String,
//        val password: String,
//        val repeatPassword: String,
//        val termsAccepted: Boolean
//    ) : RegisterEvent()
}