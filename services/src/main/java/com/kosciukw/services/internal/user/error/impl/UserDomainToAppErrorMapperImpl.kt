package com.kosciukw.services.internal.user.error.impl

import android.content.Context
import com.kosciukw.services.api.user.error.UserDomainError
import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.core.R as SharedR

class UserDomainToAppErrorMapperImpl(
    private val context: Context
) : UserDomainToAppErrorMapper {

    override fun map(error: DomainError) = when (error) {
        is UserDomainError -> mapUserDomainError(error)
        else -> AppError.TechnicalError.Unknown(message = error.message)
    }

    private fun mapUserDomainError(error: DomainError) = when (error) {
        is UserDomainError.AuthenticationError -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = context.getString(SharedR.string.error_authentication)
        )

        is UserDomainError.EmailAlreadyRegistered -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = context.getString(SharedR.string.error_authentication)
        )

        else -> AppError.InfoError(
            technicalMessage = error.message,
            uiMessage = context.getString(SharedR.string.error_something_went_wrong)
        )
    }
}
