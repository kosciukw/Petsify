package pl.kosciukw.petsify.iosapp

import com.kosciukw.services.api.user.error.UserDomainError
import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError

internal class IosUserDomainToAppErrorMapper : UserDomainToAppErrorMapper {

    override fun map(error: DomainError): AppError = when (error) {
        is UserDomainError.AuthenticationError -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = "Authentication failed"
        )

        is UserDomainError.EmailAlreadyRegistered -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = "Email is already registered"
        )

        is UserDomainError.UserLocked -> AppError.TechnicalError.UserIsLocked(
            message = error.message
        )

        else -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = "Something went wrong"
        )
    }
}
