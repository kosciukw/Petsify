package pl.kosciukw.petsify.error.mapper

import com.kosciukw.services.data.user.repository.error.model.UserDomainError
import com.kosciukw.services.data.user.service.user.error.UserDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.AppError
import pl.kosciukw.petsify.shared.error.CoreDomainError
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper

class AppIntegrationDomainToAppErrorMapper(
    private val userDomainErrorMapper: UserDomainToAppErrorMapper,
    private val coreDomainToAppErrorMapper: CoreDomainToAppErrorMapper
) : IntegrationErrorMapper {

    override fun map(error: Throwable) = when (error) {
        is UserDomainError -> userDomainErrorMapper.map(error = error)
        is CoreDomainError -> coreDomainToAppErrorMapper.map(error = error)
        else -> AppError.TechnicalError.Unknown(message = error.message ?: "Unknown error")
    }
}