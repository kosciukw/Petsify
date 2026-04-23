package pl.kosciukw.petsify.shared.validator

import pl.kosciukw.petsify.shared.utils.clear

class EmailIdentifier(
    val email: CharArray
) : Identifier() {
    
    override fun clear() {
        email.clear()
    }
    
    fun copyOf() = EmailIdentifier(
        email = email.copyOf()
    )
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmailIdentifier) return false
        
        if (! email.contentEquals(other.email)) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        return email.contentHashCode()
    }
}
