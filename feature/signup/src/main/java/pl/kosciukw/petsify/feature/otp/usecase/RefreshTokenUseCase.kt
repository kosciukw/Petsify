package pl.kosciukw.petsify.feature.otp.usecase

import com.kosciukw.services.data.user.model.domain.AuthSessionDomainModel
import com.kosciukw.services.data.user.model.domain.RefreshTokenDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<AuthSessionDomainModel, Throwable>, RefreshTokenUseCase.Params>() {

    data class Params(
        val refreshToken: String
    ) {
        override fun toString() = String.empty()
    }

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = RefreshTokenDomainModel(
            refreshToken = params.refreshToken
        )

        runCatching {
            userService.refreshToken(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
