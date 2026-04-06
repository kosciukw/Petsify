package pl.kosciukw.petsify.shared.utils

import kotlin.random.Random

fun String.Companion.empty() = ""


fun CharArray.clear() {
    for (i in indices) {
        this[i] = Random.nextInt(32, 127).toChar()
    }
}
