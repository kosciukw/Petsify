package pl.kosciukw.petsify.shared.validator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.email.EmailValidatorImpl
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

@Module
@InstallIn(SingletonComponent::class)
object ValidatorsModule {

    @Provides
    fun provideEmailValidator(): EmailValidator = EmailValidatorImpl()

    @Provides
    fun provideNotEmptyValidator(): NotEmptyValidator<CharArray> = NotEmptyValidator()
}