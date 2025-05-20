package pl.kosciukw.petsify.shared.error

sealed class CoreDomainError(message: String) : DomainError(message = HEADER + message) {

    class RequestCancelled(message: String?) : CoreDomainError(message = message ?: "Request cancelled")

    class NoCertificatePinning(message: String?) : CoreDomainError(message ?: "No certificate pinning error")

    class NoInternetConnection(message: String) : CoreDomainError(message)

    class UnexpectedMissingData(vararg missingData: String?) : CoreDomainError(
        "Unexpected missing data: " + missingData.joinToString(separator = "; ")
    )

    class FirebaseRequestFailed(message: String) : CoreDomainError(message)

    class TakingPhotoFailed(message: String) : CoreDomainError(message)

    class NoSession(message: String) : CoreDomainError(message)
    class UnknownError(message: String) : CoreDomainError(message)

    companion object {
        private const val HEADER = "CoreDomainError : "
    }
}