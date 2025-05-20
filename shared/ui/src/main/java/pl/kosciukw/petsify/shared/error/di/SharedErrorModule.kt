package pl.kosciukw.petsify.shared.error.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.impl.CoreDomainToAppErrorMapperImpl

@Module
@InstallIn(SingletonComponent::class)
object SharedErrorModule {

    @Provides
    fun providesCoreDomainToAppErrorMapper(
        @ApplicationContext context: Context
    ): CoreDomainToAppErrorMapper = CoreDomainToAppErrorMapperImpl(
        context = context
    )
}