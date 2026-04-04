package pl.kosciukw.petsify.feature.main.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.main.presentation.ui.AddPetViewModel

val mainModule = module {
    factoryOf(::AddPetViewModel)
}
