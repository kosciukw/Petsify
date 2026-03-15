package pl.kosciukw.petsify.feature.splash.usecase

import com.kosciukw.services.api.session.SessionService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase

class IsSignedInUseCase(
    private val sessionService: SessionService
) : UseCase<ResultOrFailure<Boolean, Throwable>, Unit>() {

    override fun action(params: Unit) = flow {
        emit(ResultOrFailure.Loading)

        runCatching {
            sessionService.isSignedIn()
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
