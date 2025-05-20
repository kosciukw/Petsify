package pl.kosciukw.petsify.feature.pairdevice.presentation

import pl.kosciukw.petsify.shared.error.DomainError

sealed interface LoginErrorEvent {
    data class Error(val error: DomainError): LoginErrorEvent
}