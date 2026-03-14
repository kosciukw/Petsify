package pl.kosciukw.petsify.shared.utils

import java.security.SecureRandom

fun String.Companion.empty() = ""


fun CharArray.clear() {
    val secureRandom = SecureRandom()
    for (i in indices) {
        this[i] = secureRandom.nextInt().toChar()
    }
}