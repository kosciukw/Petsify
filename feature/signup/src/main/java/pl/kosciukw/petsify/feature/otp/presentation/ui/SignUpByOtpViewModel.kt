package pl.kosciukw.petsify.feature.otp.presentation.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.otp.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpState
import pl.kosciukw.petsify.feature.otp.usecase.FinalizeOtpRegistrationUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.presentation.viewmodel.BaseViewModel

class SignUpByOtpViewModel(
    private val finalizeOtpRegistrationUseCase: FinalizeOtpRegistrationUseCase,
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<SignUpByOtpEvent, SignUpByOtpState, SignUpByOtpAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = SignUpByOtpState()

    private var isOptValid = false
    private var navArgs: SignUpByOtpNavArgs? = null

    override fun onTriggerEvent(event: SignUpByOtpEvent) {
        when (event) {
            is SignUpByOtpEvent.OnOtpProvided -> onOtpTextProvided(event.value)
            is SignUpByOtpEvent.OnConfirmButtonClicked -> onConfirmButtonClicked()
        }
    }

    fun onNavArgsProvided(navArgs: SignUpByOtpNavArgs) {
        this.navArgs = navArgs
    }

    private fun onOtpTextProvided(otp: String) {
        isOptValid = otp.length == OTP_LENGTH
        setState {
            copy(
                isOtpValidErrorEnabled = false,
                inputOtp = otp
            )
        }
        handleButtonState()
    }

    private fun onConfirmButtonClicked() {
        handleButtonState()
        if (!_state.value.isSignUpButtonStateEnabled) {
            setState { copy(isOtpValidErrorEnabled = true) }
            return
        }

        navArgs?.let {
            finalizeOtpRegistration(
                otp = _state.value.inputOtp,
                email = it.email,
                termsAccepted = it.termsAccepted,
                name = it.name,
                password = it.password,
                marketingAccepted = it.marketingAccepted
            )
        } ?: onFailure(error = IllegalStateException("Nav args is null"))
    }

    private fun finalizeOtpRegistration(
        email: String,
        password: String,
        name: String,
        termsAccepted: Boolean,
        marketingAccepted: Boolean,
        otp: String
    ) {
        viewModelScope.launch {
            finalizeOtpRegistrationUseCase.action(
                FinalizeOtpRegistrationUseCase.Params(
                    email = email,
                    password = password,
                    termsAccepted = termsAccepted,
                    name = name,
                    otp = otp,
                    marketingAccepted = marketingAccepted
                )
            ).collect { result ->
                when (result) {
                    is ResultOrFailure.Loading -> {
                        setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
                    }

                    is ResultOrFailure.Success -> {
                        setState { copy(progressBarState = ProgressBarState.Idle) }

                        setAction {
                            SignUpByOtpAction.Navigation.NavigateToMain
                        }
                    }

                    is ResultOrFailure.Failure -> {
                        onFailure(error = result.error)
                        setState {
                            copy(
                                progressBarState = ProgressBarState.Idle,
                                isOtpValidErrorEnabled = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleButtonState() {
        val enabled = isOptValid
        setState { copy(isSignUpButtonStateEnabled = enabled) }
    }

    companion object {
        private const val OTP_LENGTH = 6
    }
}
