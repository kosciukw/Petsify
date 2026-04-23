package pl.kosciukw.petsify.iosapp

import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper

internal class IosCoreDomainToAppErrorMapper : CoreDomainToAppErrorMapper {

    override fun map(error: DomainError): AppError = when (error) {
        is CoreDomainError.NoInternetConnection -> AppError.InfoError(
            uiMessage = "No internet connection",
            technicalMessage = error.message
        )

        is CoreDomainError.NoSession -> AppError.TechnicalError.SessionExpired(
            message = error.message
        )

        else -> AppError.TechnicalError.Unknown(message = error.message)
    }
}
