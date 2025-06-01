package pl.kosciukw.petsify.feature.signup.usecase

import com.kosciukw.services.data.user.model.domain.SignUpDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<Unit, Throwable>, SignUpUseCase.Params>() {

    data class Params(
        val email: String,
        val password: String,
        val name: String,
        val termsAccepted: Boolean,
        val marketingAccepted: Boolean
    ) {
        override fun toString() = String.empty()
    }

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = SignUpDomainModel(
            email = params.email,
            password = params.password,
            name = params.name,
            termsAccepted = params.termsAccepted,
            marketingAccepted = params.marketingAccepted
        )

        runCatching {
            userService.signUp(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}