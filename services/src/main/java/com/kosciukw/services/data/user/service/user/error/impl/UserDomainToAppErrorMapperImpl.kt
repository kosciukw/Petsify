package com.kosciukw.services.data.user.service.user.error.impl

import android.content.Context
import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.data.user.service.user.error.UserDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.DomainError
import javax.inject.Inject
import pl.kosciukw.petsify.shared.ui.R as SharedR

class UserDomainToAppErrorMapperImpl @Inject constructor(
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

        else -> AppError.InfoError(
            technicalMessage =  error.message,
            uiMessage = context.getString(SharedR.string.error_something_went_wrong)
        )
    }
}