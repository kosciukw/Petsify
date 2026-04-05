package pl.kosciukw.petsify.feature.otp.presentation

import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.presentation.components.view.ViewState
import pl.kosciukw.petsify.shared.utils.empty

data class SignUpByOtpState(
    val inputOtp: String = String.empty(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isOtpValidErrorEnabled: Boolean = false,
    val isSignUpButtonStateEnabled: Boolean = false
) : ViewState
