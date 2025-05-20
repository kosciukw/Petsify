package pl.kosciukw.petsify.feature.pairdevice.presentation

import pl.kosciukw.petsify.shared.data.network.NetworkState
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.view.ViewState
import pl.kosciukw.petsify.shared.utils.empty

data class LoginState(
    val inputEmail: String = String.empty(),
    val inputPassword: String = String.empty(),
    val isLoginButtonEnabled: Boolean = false,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isEmailValidationErrorEnabled: Boolean = false,
    val isPasswordValidationErrorEnabled: Boolean = false,
    val networkState: NetworkState = NetworkState.Established,
) : ViewState