package pl.kosciukw.petsify.feature.pairdevice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidator
import pl.kosciukw.petsify.feature.pairdevice.domain.EmailIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    fun provideEmailIdentifierValidator(
        emailValidator: EmailValidator,
        notEmptyValidator: NotEmptyValidator<CharArray>
    ): EmailIdentifierValidator = EmailIdentifierValidatorImpl(
        emailValidator = emailValidator,
        notEmptyValidator = notEmptyValidator
    )
}