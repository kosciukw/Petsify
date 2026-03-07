package pl.kosciukw.petsify.feature.home.presentation.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.kosciukw.petsify.feature.home.presentation.HomeAction
import pl.kosciukw.petsify.feature.home.presentation.HomeEvent
import pl.kosciukw.petsify.feature.home.presentation.HomeState
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    integrationErrorMapper: IntegrationErrorMapper
) : BaseViewModel<HomeEvent, HomeState, HomeAction>(
    integrationErrorMapper = integrationErrorMapper
) {

    override fun setInitialState() = HomeState()

    override fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnStart -> {
                // no-op
            }
        }
    }
}
