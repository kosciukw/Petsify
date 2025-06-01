package pl.kosciukw.petsify.feature.signup.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class SignUpEvent : ViewEvent {
    data class OnNameTextChanged(val value: String) : SignUpEvent()
    data class OnEmailTextChanged(val value: String) : SignUpEvent()
    data class OnPasswordChanged(val value: String) : SignUpEvent()
    data class OnConfirmPasswordChanged(val value: String) : SignUpEvent()
    data class OnTermsAcceptedChanged(val accepted: Boolean) : SignUpEvent()
    data class OnMarketingAcceptedChanged(val accepted: Boolean) : SignUpEvent()
}