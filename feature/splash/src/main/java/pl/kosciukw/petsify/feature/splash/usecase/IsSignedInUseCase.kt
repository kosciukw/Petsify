package pl.kosciukw.petsify.feature.splash.usecase

import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<Boolean, Throwable>, Unit>() {

    override fun action(params: Unit) = flow {
        emit(ResultOrFailure.Loading)

        runCatching {
            userService.isSignedIn()
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}