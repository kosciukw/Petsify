package pl.kosciukw.petsify.di

import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.kosciukw.petsify.error.mapper.AppIntegrationDomainToAppErrorMapper
import pl.kosciukw.petsify.error.mapper.UserDomainToAppErrorMapperImpl
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider
import pl.kosciukw.petsify.strings.AndroidFeatureStringsProvider

val appModule = module {
    single<UserDomainToAppErrorMapper> { UserDomainToAppErrorMapperImpl(androidContext()) }
    singleOf(::AppIntegrationDomainToAppErrorMapper) { bind<IntegrationErrorMapper>() }
    single<FeatureStringsProvider> { AndroidFeatureStringsProvider(androidContext()) }
}
