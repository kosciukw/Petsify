package pl.kosciukw.petsify.shared.validator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidator
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.email.EmailValidatorImpl
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.password.PasswordValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordValidatorImpl

@Module
@InstallIn(SingletonComponent::class)
object ValidatorsModule {

    @Provides
    fun provideEmailValidator(): EmailValidator = EmailValidatorImpl()

    @Provides
    fun provideNotEmptyValidator(): NotEmptyValidator<CharArray> = NotEmptyValidator()

    @Provides
    fun provideEmailIdentifierValidator(
        emailValidator: EmailValidator,
        notEmptyValidator: NotEmptyValidator<CharArray>
    ): EmailIdentifierValidator = EmailIdentifierValidatorImpl(
        emailValidator = emailValidator,
        notEmptyValidator = notEmptyValidator
    )

    @Provides
    fun providePasswordValidator(): PasswordValidator = PasswordValidatorImpl()

    @Provides
    fun providePasswordIdentifierValidator(
        passwordValidator: PasswordValidator,
        notEmptyValidator: NotEmptyValidator<CharArray>
    ): PasswordIdentifierValidator = PasswordIdentifierValidatorImpl(
        passwordValidator = passwordValidator,
        notEmptyValidator = notEmptyValidator
    )
}