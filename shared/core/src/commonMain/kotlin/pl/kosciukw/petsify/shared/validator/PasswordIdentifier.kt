package pl.kosciukw.petsify.shared.validator

import pl.kosciukw.petsify.shared.utils.clear

class PasswordIdentifier(
    val password: CharArray
) : Identifier() {

    override fun clear() {
        password.clear()
    }

    fun copyOf() = PasswordIdentifier(
        password = password.copyOf()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PasswordIdentifier) return false

        if (! password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        return password.contentHashCode()
    }
}
