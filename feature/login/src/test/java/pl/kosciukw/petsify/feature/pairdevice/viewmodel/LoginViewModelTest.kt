package pl.kosciukw.petsify.feature.pairdevice.viewmodel

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidator
import pl.kosciukw.petsify.feature.pairdevice.presentation.LoginEvent
import pl.kosciukw.petsify.feature.pairdevice.presentation.ui.LoginViewModel
import pl.kosciukw.petsify.feature.pairdevice.usecase.PairDeviceUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.ui.components.progress.ProgressBarState
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val pairDeviceUseCase: PairDeviceUseCase = mockk()
    private val emailValidator: EmailIdentifierValidator = mockk()
    private val notEmptyValidator: NotEmptyValidator<CharArray> = mockk()
    private val errorMapper: IntegrationErrorMapper = mockk()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = LoginViewModel(
            pairDeviceUseCase = pairDeviceUseCase,
            emailIdentifierValidator = emailValidator,
            notEmptyValidator = notEmptyValidator,
            integrationErrorMapper = errorMapper
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Whe valid email and password entered Then should enable login button`() = runTest {
        every { emailValidator.isValid(any()) } returns IdentifierState.Valid
        every { notEmptyValidator.isValid(any()) } returns true

        viewModel.onTriggerEvent(
            LoginEvent.OnEmailTextChanged(
                value = "test@example.com"
            )
        )
        viewModel.onTriggerEvent(
            LoginEvent.OnPasswordTextChanged(
                value = "password"
            )
        )

        val state = viewModel.state.value
        assertTrue(state.isLoginButtonEnabled)
        assertFalse(state.isEmailValidationErrorEnabled)
        assertFalse(state.isPasswordValidationErrorEnabled)
    }

    @Test
    fun `When pair device called And result is success Then  should navigate to main`() = runTest {
        //Given
        val givenEmail = "test@example.com"
        val givenPassword = "password"

        val givenAccessTokenApiModel = AccessTokenApiModel(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )

        coEvery {
            pairDeviceUseCase.action(
                PairDeviceUseCase.Params(
                    email = givenEmail,
                    password = givenPassword
                )
            )
        } returns flow {
            emit(ResultOrFailure.Loading)
            emit(ResultOrFailure.Success(givenAccessTokenApiModel))
        }

        every { emailValidator.isValid(any()) } returns IdentifierState.Valid
        every { notEmptyValidator.isValid(any()) } returns true

        viewModel.onTriggerEvent(LoginEvent.OnEmailTextChanged(givenEmail))
        viewModel.onTriggerEvent(LoginEvent.OnPasswordTextChanged(givenPassword))

        // When
        viewModel.onTriggerEvent(
            LoginEvent.Login(
                email = givenEmail,
                password = givenPassword
            )
        )

        advanceUntilIdle()

        //Then
        val currentState = viewModel.state.value
        assertEquals(ProgressBarState.Idle, currentState.progressBarState)
        assertEquals(givenEmail, currentState.inputEmail)
    }

    @Test
    fun `When invalid email entered Then should show email validation error`() = runTest {
        // Given
        val invalidEmail = "invalid-email"
        every { emailValidator.isValid(any()) } returns IdentifierState.Invalid

        viewModel.onTriggerEvent(LoginEvent.OnEmailTextChanged(invalidEmail))

        // When
        val state = viewModel.state.value

        // Then
        assertFalse(state.isLoginButtonEnabled)
        assertTrue(state.isEmailValidationErrorEnabled)
    }
}