package com.kosciukw.services.internal.user.mapper.di

import com.kosciukw.services.internal.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.AccessTokenApiToAuthSessionDomainModelMapper
import com.kosciukw.services.internal.user.mapper.impl.AccessTokenApiToAuthSessionDomainModelMapperImpl
import com.kosciukw.services.internal.user.mapper.impl.LoginByPasswordDomainToRequestModelMapperImpl
import com.kosciukw.services.internal.user.mapper.impl.StartOtpRegistrationDomainToRequestModelMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServicesMappersModule {

    @Provides
    fun provideAccessTokenApiToAuthSessionDomainModelMapper(): AccessTokenApiToAuthSessionDomainModelMapper =
        AccessTokenApiToAuthSessionDomainModelMapperImpl()

    @Provides
    fun provideLoginByPasswordDomainToRequestModelMapper(): LoginByPasswordDomainToRequestModelMapper =
        LoginByPasswordDomainToRequestModelMapperImpl()

    @Provides
    fun provideStartOtpRegistrationDomainToRequestModelMapper(): StartOtpRegistrationDomainToRequestModelMapper =
        StartOtpRegistrationDomainToRequestModelMapperImpl()

    @Provides
    fun provideFinalizeOtpRegistrationDomainToRequestModelMapper(): FinalizeOtpRegistrationDomainToRequestModelMapper =
        FinalizeOtpRegistrationDomainToRequestModelMapperImpl()
}
