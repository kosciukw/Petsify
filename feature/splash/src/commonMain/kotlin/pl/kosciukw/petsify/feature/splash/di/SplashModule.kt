package pl.kosciukw.petsify.feature.splash.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.splash.presentation.ui.SplashViewModel
import pl.kosciukw.petsify.feature.splash.usecase.IsSignedInUseCase

val splashModule = module {
    factoryOf(::IsSignedInUseCase)
    factoryOf(::SplashViewModel)
}
