package pl.kosciukw.petsify.shared.network

import kotlinx.coroutines.suspendCancellableCoroutine
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.mapper.ApiToDomainErrorMapper
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun <T> networkRequest(
    networkStateProvider: NetworkStateProvider,
    apiCall: () -> T
): T =
    if (networkStateProvider.isInternetConnectionAvailable()) apiCall()
    else throw CoreDomainError.NoInternetConnection(message = "From network request in network state provider")

suspend fun <T> suspendNetworkRequest(
    networkStateProvider: NetworkStateProvider,
    apiCall: suspend () -> T
): T =
    if (networkStateProvider.isInternetConnectionAvailable()) apiCall()
    else throw CoreDomainError.NoInternetConnection(message = "From suspend network request in network state provider")

suspend fun suspendCallback(
    errorMapper: ApiToDomainErrorMapper? = null,
    wrappedApiCall: (() -> Unit, (Throwable) -> Unit) -> Unit
) {
    return suspendCancellableCoroutine { continuation ->
        wrappedApiCall(
            { continuation.resume(Unit) },
            { continuation.resumeWithException(errorMapper?.map(it) ?: it) }
        )
    }
}