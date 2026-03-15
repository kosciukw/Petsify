package pl.kosciukw.petsify.feature.login.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.login.presentation.ui.LoginViewModel
import pl.kosciukw.petsify.feature.login.usecase.LoginDeviceUseCase

val loginModule = module {
    factoryOf(::LoginDeviceUseCase)
    factoryOf(::LoginViewModel)
}
