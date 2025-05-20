package pl.kosciukw.petsify.feature.pairdevice.presentation

import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.ui.components.view.ViewEvent

sealed class LoginEvent : ViewEvent {
    data class OnEmailTextChanged(val value: String) : LoginEvent()
    data class OnPasswordTextChanged(val value: String) : LoginEvent()
    data class Login(val email: String, val password: String) : LoginEvent()
    data object OnRetryNetwork : LoginEvent()
    data class OnUpdateNetworkState(val networkState: NetworkState) : LoginEvent()
}