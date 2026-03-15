package pl.kosciukw.petsify.feature.otp.presentation

import pl.kosciukw.petsify.shared.presentation.components.view.ViewEvent

sealed class SignUpByOtpEvent : ViewEvent {
    data class OnOtpProvided(val value: String) : SignUpByOtpEvent()
    data object OnConfirmButtonClicked : SignUpByOtpEvent()
}