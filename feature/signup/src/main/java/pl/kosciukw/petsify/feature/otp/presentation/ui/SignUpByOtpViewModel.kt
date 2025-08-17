package pl.kosciukw.petsify.feature.otp.presentation.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.otp.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpState
import pl.kosciukw.petsify.feature.otp.usecase.FinalizeOtpRegistrationUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.ui.viewmodel.BaseViewModel
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import javax.inject.Inject

@HiltViewModel
class SignUpByOtpViewModel @Inject constructor(
    private val finalizeOtpRegistrationUseCase: FinalizeOtpRegistrationUseCase,
    private val notEmptyValidator: NotEmptyValidator<CharArray>,
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<SignUpByOtpEvent, SignUpByOtpState, SignUpByOtpAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = SignUpByOtpState()

    private var isOptValid = false

    override fun onTriggerEvent(event: SignUpByOtpEvent) {
        when (event) {
            is SignUpByOtpEvent.OnOtpProvided -> onOtpTextChanged(event.value)
        }
    }

    fun onNavArgsProvided(navArgs: SignUpByOtpNavArgs) {
        println("TEST_TAG SignUpByOtpViewModel onNavArgsProvided ${navArgs.email}")
    }

    private fun onOtpTextChanged(otp: String) {
        isOptValid = otp.isNotBlank()
//        setState {
//            copy(
//                inputName = name,
//                isNameValidationErrorEnabled = !isNameValid
//            )
//        }
        handleButtonState()
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

                        // TODO 26.06.2025 handle finalize registration process
                    }

                    is ResultOrFailure.Failure -> {
                        onFailure(error = result.error)
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                    }
                }
            }
        }
    }

    private fun handleButtonState() {
        val enabled = isOptValid
//        setState { copy(isSignUpButtonStateEnabled = enabled) }
    }
}