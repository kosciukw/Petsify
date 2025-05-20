package pl.kosciukw.petsify.shared.extensions

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import java.util.LinkedList

fun <T> MutableState<LinkedList<T>>.appendToMessageQueue(item: T) {
    value.add(item)
    value = LinkedList(value)
}

fun <T> MutableState<LinkedList<T>>.removeHeadMessage() {
    if (value.isNotEmpty()) {
        value.remove()
        value = LinkedList(value)
    }
}

fun Context.makeToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}