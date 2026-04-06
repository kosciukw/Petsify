package pl.kosciukw.petsify.shared.error.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.impl.CoreDomainToAppErrorMapperImpl

val sharedErrorModule = module {
    single<CoreDomainToAppErrorMapper> {
        CoreDomainToAppErrorMapperImpl(
            context = androidContext()
        )
    }
}
