package pl.kosciukw.petsify.feature.addpet.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetSpeciesDomainToUiModelMapper
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.PetWeightUnitDomainToUiModelMapper
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.impl.PetSpeciesDomainToUiModelMapperImpl
import pl.kosciukw.petsify.feature.addpet.presentation.mapper.impl.PetWeightUnitDomainToUiModelMapperImpl
import pl.kosciukw.petsify.feature.addpet.presentation.ui.AddPetViewModel

val addPetModule = module {
    singleOf(::PetSpeciesDomainToUiModelMapperImpl) { bind<PetSpeciesDomainToUiModelMapper>() }
    singleOf(::PetWeightUnitDomainToUiModelMapperImpl) { bind<PetWeightUnitDomainToUiModelMapper>() }
    factoryOf(::AddPetViewModel)
}
