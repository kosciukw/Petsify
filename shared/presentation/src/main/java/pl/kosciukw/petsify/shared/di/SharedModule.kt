package pl.kosciukw.petsify.shared.di

import org.koin.dsl.module
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.impl.NetworkStateProviderImpl

val sharedModule = module {
    single<NetworkStateProvider> { NetworkStateProviderImpl() }
}
