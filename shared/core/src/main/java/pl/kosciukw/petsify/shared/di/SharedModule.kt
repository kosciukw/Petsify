package pl.kosciukw.petsify.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.network.impl.NetworkStateProviderImpl

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    fun provideNetworkStateProvider() : NetworkStateProvider = NetworkStateProviderImpl()
}