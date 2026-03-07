package pl.kosciukw.petsify.feature.otp.usecase

import com.kosciukw.services.data.user.model.domain.FinalizeOtpRegistrationDomainModel
import com.kosciukw.services.data.user.service.user.UserService
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase
import pl.kosciukw.petsify.shared.utils.empty
import javax.inject.Inject

class FinalizeOtpRegistrationUseCase @Inject constructor(
    private val userService: UserService
) : UseCase<ResultOrFailure<Unit, Throwable>, FinalizeOtpRegistrationUseCase.Params>() {

    data class Params(
        val email: String,
        val password: String,
        val name: String,
        val termsAccepted: Boolean,
        val marketingAccepted: Boolean,
        val otp: String
    ) {
        override fun toString() = String.empty()
    }

    override fun action(params: Params) = flow {
        emit(ResultOrFailure.Loading)

        val request = FinalizeOtpRegistrationDomainModel(
            email = params.email,
            otp = params.otp,
            password = params.password,
            termsAccepted = params.termsAccepted,
            marketingAccepted = params.marketingAccepted,
            name = params.name
        )

        runCatching {
            userService.finalizeOtpRegistration(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
