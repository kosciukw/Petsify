package pl.kosciukw.petsify.feature.otp.presentation

import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.components.view.ViewState
import pl.kosciukw.petsify.shared.utils.empty

data class SignUpByOtpState(
    val inputOtp: String = String.empty(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isOtpValidErrorEnabled: Boolean = false
) : ViewState