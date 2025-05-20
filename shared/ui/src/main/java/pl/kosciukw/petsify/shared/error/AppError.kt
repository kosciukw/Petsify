package pl.kosciukw.petsify.shared.error

sealed class AppError(override val message: String) : Throwable(message) {

    class InfoError(
        val uiMessage: String,
        val technicalMessage: String
    ) : AppError(message = technicalMessage)

    sealed class TechnicalError(
        message: String
    ) : AppError(message = message) {

        class Unknown(message: String) : TechnicalError(message = message)

        class UserIsLocked(message: String) : TechnicalError(message = message)

        class SessionExpired(message: String) : TechnicalError(message = message)
    }
}