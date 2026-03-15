package pl.kosciukw.petsify.feature.settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.settings.presentation.ui.SettingsViewModel

val settingsModule = module {
    factoryOf(::SettingsViewModel)
}
