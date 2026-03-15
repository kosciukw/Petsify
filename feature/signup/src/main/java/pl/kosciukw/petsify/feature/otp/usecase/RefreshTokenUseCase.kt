package pl.kosciukw.petsify.feature.otp.usecase

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.session.SessionService
import com.kosciukw.services.api.session.model.RefreshTokenDomainModel
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.utils.empty

class RefreshTokenUseCase(
    private val sessionService: SessionService
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
            sessionService.refreshToken(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
