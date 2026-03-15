package pl.kosciukw.petsify.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.kosciukw.petsify.error.mapper.AppIntegrationDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper

val appModule = module {
    singleOf(::AppIntegrationDomainToAppErrorMapper) { bind<IntegrationErrorMapper>() }
}
