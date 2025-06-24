package pl.kosciukw.petsify.feature.signup.usecase

import com.kosciukw.services.data.user.model.domain.StartOtpRegistrationDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class StartOtpRegistrationUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<Unit, Throwable>, StartOtpRegistrationUseCase.Params>() {

    data class Params(
        val email: String
    ) {
        override fun toString() = String.empty()
    }

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = StartOtpRegistrationDomainModel(
            email = params.email
        )

        runCatching {
            userService.startOtpRegistration(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}