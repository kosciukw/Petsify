package pl.kosciukw.petsify.shared.result

sealed interface ResultOrFailure<out D, out E: Throwable> {
    data class Success<out D>(val data: D) : ResultOrFailure<D, Nothing>
    data class Failure<out E: Throwable>(val error: E) : ResultOrFailure<Nothing, E>
    data object Loading : ResultOrFailure<Nothing, Nothing>
}

inline fun <T, E: Throwable, R> ResultOrFailure<T, E>.map(map: (T) -> R): ResultOrFailure<R, E> {
    return when (this) {
        is ResultOrFailure.Success -> ResultOrFailure.Success(map(data))
        is ResultOrFailure.Failure -> this
        ResultOrFailure.Loading -> ResultOrFailure.Loading
    }
}

fun <T, E: Throwable> ResultOrFailure<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return when (this) {
        is ResultOrFailure.Success -> ResultOrFailure.Success(Unit)
        is ResultOrFailure.Failure -> this
        ResultOrFailure.Loading -> ResultOrFailure.Loading
    }
}

inline fun <T, E: Throwable> ResultOrFailure<T, E>.onSuccess(action: (T) -> Unit): ResultOrFailure<T, E> {
    return when (this) {
        is ResultOrFailure.Success -> {
            action(data)
            this
        }
        else -> this
    }
}

inline fun <T, E: Throwable> ResultOrFailure<T, E>.onError(action: (E) -> Unit): ResultOrFailure<T, E> {
    return when (this) {
        is ResultOrFailure.Failure -> {
            action(error)
            this
        }
        else -> this
    }
}

typealias EmptyResult<E> = ResultOrFailure<Unit, E>