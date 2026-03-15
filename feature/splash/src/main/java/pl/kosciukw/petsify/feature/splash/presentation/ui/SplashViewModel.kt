package pl.kosciukw.petsify.feature.splash.presentation.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.feature.splash.presentation.SplashAction
import pl.kosciukw.petsify.feature.splash.presentation.SplashEvent
import pl.kosciukw.petsify.feature.splash.presentation.SplashState
import pl.kosciukw.petsify.feature.splash.usecase.IsSignedInUseCase
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.presentation.viewmodel.BaseViewModel

class SplashViewModel(
    private val isSignedInUseCase: IsSignedInUseCase,
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<SplashEvent, SplashState, SplashAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = SplashState()

    override fun onTriggerEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStart -> isSignedIn()
        }
    }

    init {
        isSignedIn()
    }

    private fun isSignedIn() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            isSignedInUseCase.action(Unit).collect { result ->
                when (result) {
                    is ResultOrFailure.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is ResultOrFailure.Success -> {
                        setState { copy(isLoading = false) }
                        val isSignedIn = result.data

                        if (isSignedIn) setAction { SplashAction.Navigation.NavigateToMain }
                        else setAction { SplashAction.Navigation.NavigateToLogin }
                    }

                    is ResultOrFailure.Failure -> {
                        setState { copy(isLoading = false) }
                        // todo 07.09.2025 dialog
                    }
                }
            }
        }
    }

}
