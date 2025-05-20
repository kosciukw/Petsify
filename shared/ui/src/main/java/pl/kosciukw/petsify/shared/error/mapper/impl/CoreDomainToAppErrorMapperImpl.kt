package pl.kosciukw.petsify.shared.error.mapper.impl

import android.content.Context
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.DomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import javax.inject.Inject
import pl.kosciukw.petsify.shared.ui.R as SharedR

class CoreDomainToAppErrorMapperImpl @Inject constructor(
    private val context: Context
) : CoreDomainToAppErrorMapper {

    override fun map(error: DomainError) = when (error) {
        is CoreDomainError -> mapCoreDomainError(error)
        else -> AppError.TechnicalError.Unknown(message = error.message)
    }

    private fun mapCoreDomainError(error: CoreDomainError) = when (error) {
        is CoreDomainError.NoInternetConnection -> AppError.InfoError(
            uiMessage = context.getString(SharedR.string.error_no_internet_connection),
            technicalMessage = error.message
        )

        is CoreDomainError.NoSession -> AppError.TechnicalError.SessionExpired(
            message = error.message
        )

        else -> AppError.TechnicalError.Unknown(message = error.message)
    }
}