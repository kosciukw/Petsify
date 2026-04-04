package pl.kosciukw.petsify.feature.addpet.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.addpet.presentation.ui.AddPetViewModel

val addPetModule = module {
    factoryOf(::AddPetViewModel)
}
