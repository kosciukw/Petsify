package pl.kosciukw.petsify.feature.signup.usecase

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<AccessTokenApiModel, Throwable>, SignUpUseCase.Params>() {

    data class Params(
        val email: String,
        val password: String
    )

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = PairByPasswordDomainModel(
            email = params.email,
            password = params.password
        )

        runCatching {
            userService.pairDeviceByPassword(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}