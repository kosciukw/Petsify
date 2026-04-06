package pl.kosciukw.petsify.iosapp

import com.kosciukw.services.api.user.error.UserDomainError
import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper

internal class IosIntegrationErrorMapper(
    private val userDomainErrorMapper: UserDomainToAppErrorMapper,
    private val coreDomainToAppErrorMapper: CoreDomainToAppErrorMapper
) : IntegrationErrorMapper {

    override fun map(error: Throwable): AppError = when (error) {
        is UserDomainError -> userDomainErrorMapper.map(error)
        is CoreDomainError -> coreDomainToAppErrorMapper.map(error)
        else -> AppError.TechnicalError.Unknown(error.message ?: "Unknown error")
    }
}
