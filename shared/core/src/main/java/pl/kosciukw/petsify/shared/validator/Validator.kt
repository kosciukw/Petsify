package pl.kosciukw.petsify.shared.validator

interface Validator<in Data> {
    
    fun isValid(data: Data): Boolean
}