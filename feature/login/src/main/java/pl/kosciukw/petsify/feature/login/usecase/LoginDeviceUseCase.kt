package pl.kosciukw.petsify.feature.login.usecase

import com.kosciukw.services.data.user.model.api.response.AccessTokenApiModel
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.*
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class LoginDeviceUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<AccessTokenApiModel, Throwable>, LoginDeviceUseCase.Params>() {

    data class Params(
        val email: String,
        val password: String
    ) {
        override fun toString() = String.empty()
    }

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = LoginByPasswordDomainModel(
            email = params.email,
            password = params.password
        )

        runCatching {
            userService.loginDeviceByPassword(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}