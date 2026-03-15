package pl.kosciukw.petsify.shared.validator

import java.io.Serializable

sealed class Identifier : Serializable {
    abstract fun clear()
}