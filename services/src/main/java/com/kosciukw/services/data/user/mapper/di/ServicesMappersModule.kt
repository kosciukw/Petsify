package com.kosciukw.services.data.user.mapper.di

import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.SignUpDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.impl.LoginByPasswordDomainToRequestModelMapperImpl
import com.kosciukw.services.data.user.mapper.impl.SignUpDomainToRequestModelMapperImpl
import com.kosciukw.services.data.user.mapper.impl.StartOtpRegistrationDomainToRequestModelMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServicesMappersModule {

    @Provides
    fun provideLoginByPasswordDomainToRequestModelMapper(): LoginByPasswordDomainToRequestModelMapper =
        LoginByPasswordDomainToRequestModelMapperImpl()

    @Provides
    fun provideSignUpDomainToRequestModelMapper(): SignUpDomainToRequestModelMapper =
        SignUpDomainToRequestModelMapperImpl()

    @Provides
    fun provideStartOtpRegistrationDomainToRequestModelMapper(): StartOtpRegistrationDomainToRequestModelMapper =
        StartOtpRegistrationDomainToRequestModelMapperImpl()
}