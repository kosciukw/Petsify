package pl.kosciukw.petsify.shared.validator.password

import pl.kosciukw.petsify.shared.validator.IdentifierState
import pl.kosciukw.petsify.shared.validator.PasswordIdentifier
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator

class PasswordIdentifierValidatorImpl(
    private val passwordValidator: PasswordValidator,
    private val notEmptyValidator: NotEmptyValidator<CharArray>
) : PasswordIdentifierValidator {

    override fun isValid(identifier: PasswordIdentifier) = with(identifier) {
        when {
            ! notEmptyValidator.isValid(password) -> IdentifierState.Empty
            passwordValidator.isValid(password) -> IdentifierState.Valid
            else -> IdentifierState.Invalid
        }
    }
}