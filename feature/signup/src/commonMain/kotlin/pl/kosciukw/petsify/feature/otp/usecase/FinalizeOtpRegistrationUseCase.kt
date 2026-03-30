package pl.kosciukw.petsify.feature.otp.usecase

import com.kosciukw.services.api.auth.model.AuthSessionDomainModel
import com.kosciukw.services.api.registration.RegistrationService
import com.kosciukw.services.api.registration.model.FinalizeOtpRegistrationDomainModel
import kotlinx.coroutines.flow.flow
import pl.kosciukw.petsify.shared.result.ResultOrFailure
import pl.kosciukw.petsify.shared.usecase.UseCase

class FinalizeOtpRegistrationUseCase(
    private val registrationService: RegistrationService
) : UseCase<ResultOrFailure<AuthSessionDomainModel, Throwable>, FinalizeOtpRegistrationUseCase.Params>() {

    data class Params(
        val email: String,
        val password: String,
        val name: String,
        val termsAccepted: Boolean,
        val marketingAccepted: Boolean,
        val otp: String
    )

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
            registrationService.finalizeOtpRegistration(request)
        }.onSuccess { result ->
            emit(ResultOrFailure.Success(result))
        }.onFailure { error ->
            emit(ResultOrFailure.Failure(error))
        }
    }
}
