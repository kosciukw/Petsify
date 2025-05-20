package pl.kosciukw.petsify.di

import com.kosciukw.services.data.user.service.user.error.UserDomainToAppErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.error.mapper.AppIntegrationDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIntegrationMapper(
        userDomainErrorMapper: UserDomainToAppErrorMapper,
        coreDomainToAppErrorMapper: CoreDomainToAppErrorMapper
    ): IntegrationErrorMapper = AppIntegrationDomainToAppErrorMapper(
        userDomainErrorMapper = userDomainErrorMapper,
        coreDomainToAppErrorMapper = coreDomainToAppErrorMapper
    )
}