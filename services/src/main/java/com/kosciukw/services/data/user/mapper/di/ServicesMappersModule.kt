package com.kosciukw.services.data.user.mapper.di

import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.impl.PairByPasswordDomainToRequestModelMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

@Module
@InstallIn(SingletonComponent::class)
object ServicesMappersModule {

    @Provides
    fun providePairByPasswordDomainToRequestModelMapper(
        emailValidator: EmailValidator,
        notEmptyValidator: NotEmptyValidator<CharArray>
    ): PairByPasswordDomainToRequestModelMapper = PairByPasswordDomainToRequestModelMapperImpl()
}