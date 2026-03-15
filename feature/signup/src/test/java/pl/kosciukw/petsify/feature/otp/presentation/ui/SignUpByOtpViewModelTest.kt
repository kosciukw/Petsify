package pl.kosciukw.petsify.feature.otp.presentation.ui

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.feature.otp.navigation.SignUpByOtpNavArgs
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpAction
import pl.kosciukw.petsify.feature.otp.presentation.SignUpByOtpEvent
import pl.kosciukw.petsify.feature.otp.usecase.FinalizeOtpRegistrationUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.presentation.components.progress.ProgressBarState

@ExperimentalCoroutinesApi
class SignUpByOtpViewModelTest {

    private val finalizeOtpRegistrationUseCase: FinalizeOtpRegistrationUseCase = mockk()
    private val integrationErrorMapper: IntegrationErrorMapper = mockk()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SignUpByOtpViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = SignUpByOtpViewModel(
            finalizeOtpRegistrationUseCase = finalizeOtpRegistrationUseCase,
            integrationErrorMapper = integrationErrorMapper
        )
        viewModel.onNavArgsProvided(
            SignUpByOtpNavArgs(
                email = "john@petsify.com",
                password = "Password123",
                name = "John",
                termsAccepted = true,
                marketingAccepted = true
            )
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when otp has 6 digits then should enable confirm button`() = runTest {
        viewModel.onTriggerEvent(SignUpByOtpEvent.OnOtpProvided("123456"))

        val state = viewModel.state.value
        assertTrue(state.isSignUpButtonStateEnabled)
        assertFalse(state.isOtpValidErrorEnabled)
        assertEquals("123456", state.inputOtp)
    }

    @Test
    fun `when confirm clicked with invalid otp then should show otp error`() = runTest {
        viewModel.onTriggerEvent(SignUpByOtpEvent.OnOtpProvided("123"))
        viewModel.onTriggerEvent(SignUpByOtpEvent.OnConfirmButtonClicked)

        val state = viewModel.state.value
        assertFalse(state.isSignUpButtonStateEnabled)
        assertTrue(state.isOtpValidErrorEnabled)
        assertEquals(ProgressBarState.Idle, state.progressBarState)
    }

    @Test
    fun `when finalize otp succeeds then should navigate to main`() = runTest {
        val response = AccessTokenApiModel(
            accessToken = "access-token",
            refreshToken = "refresh-token"
        )

        every { finalizeOtpRegistrationUseCase.action(any()) } returns flow {
            emit(ResultOrFailure.Loading)
            emit(ResultOrFailure.Success(response))
        }

        viewModel.onTriggerEvent(SignUpByOtpEvent.OnOtpProvided("123456"))

        val actionDeferred = async { viewModel.action.first() }

        viewModel.onTriggerEvent(SignUpByOtpEvent.OnConfirmButtonClicked)
        advanceUntilIdle()

        val action = actionDeferred.await()
        assertEquals(SignUpByOtpAction.Navigation.NavigateToMain, action)
        assertEquals(ProgressBarState.Idle, viewModel.state.value.progressBarState)
    }
}
