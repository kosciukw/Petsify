package pl.kosciukw.petsify.feature.otp.presentation

import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class SignUpByOtpEvent : ViewEvent {
    data class OnOtpProvided(val value: String) : SignUpByOtpEvent()
}