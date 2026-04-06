package pl.kosciukw.petsify.shared.validator.notempty

import pl.kosciukw.petsify.shared.validator.Validator

class NotEmptyValidator<T> : Validator<T> {
    
    override fun isValid(data: T): Boolean {
        return when (data) {
            is CharSequence -> data.isNotEmpty()
            is CharArray -> data.isNotEmpty()
            else -> throw IllegalArgumentException("The type of the data is not supported by the validator. Please add a new case manually.")
        }
    }
}