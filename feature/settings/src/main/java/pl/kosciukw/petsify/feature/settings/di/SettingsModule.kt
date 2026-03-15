package pl.kosciukw.petsify.feature.settings.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsViewModel

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}
