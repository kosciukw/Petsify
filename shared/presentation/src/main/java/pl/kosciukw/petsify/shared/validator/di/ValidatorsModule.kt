package pl.kosciukw.petsify.shared.validator.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidator
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.email.EmailValidatorImpl
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchValidatorImpl
import pl.kosciukw.petsify.shared.validator.password.PasswordValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordValidatorImpl

val validatorsModule = module {
    single<EmailValidator> { EmailValidatorImpl() }
    single<NotEmptyValidator<CharArray>> { NotEmptyValidator() }
    singleOf(::EmailIdentifierValidatorImpl) { bind<EmailIdentifierValidator>() }
    single<PasswordValidator> { PasswordValidatorImpl() }
    singleOf(::PasswordIdentifierValidatorImpl) { bind<PasswordIdentifierValidator>() }
    single<PasswordMatchValidator> { PasswordMatchValidatorImpl() }
}
