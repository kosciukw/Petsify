package pl.kosciukw.petsify.feature.pairdevice.domain

import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

class EmailIdentifierValidatorImpl(
    private val emailValidator: EmailValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>
) : EmailIdentifierValidator {
    
    override fun isValid(identifier: EmailIdentifier) = with(identifier) {
        when {
            ! notEmptyValidator.isValid(email) -> IdentifierState.Empty
            emailValidator.isValid(email) -> IdentifierState.Valid
            else -> IdentifierState.Invalid
        }
    }
}