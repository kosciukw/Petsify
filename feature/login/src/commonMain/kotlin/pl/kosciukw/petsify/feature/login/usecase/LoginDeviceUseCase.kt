package pl.kosciukw.petsify.feature.login.usecase

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.auth.model.LoginByPasswordDomainModel
import kotlinx.coroutines.flow.*
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.utils.empty

class LoginDeviceUseCase(
    private val authService: AuthService
) : UseCase<ResultOrFailure<AuthSessionDomainModel, Throwable>, LoginDeviceUseCase.Params>() {

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
            authService.loginDeviceByPassword(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
