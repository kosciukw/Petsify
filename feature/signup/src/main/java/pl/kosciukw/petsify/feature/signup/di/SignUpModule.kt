package pl.kosciukw.petsify.feature.signup.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import pl.kosciukw.petsify.feature.otp.presentation.ui.SignUpByOtpViewModel
import pl.kosciukw.petsify.feature.otp.usecase.FinalizeOtpRegistrationUseCase
import pl.kosciukw.petsify.feature.otp.usecase.RefreshTokenUseCase
import pl.kosciukw.petsify.feature.signup.presentation.ui.SignUpViewModel
import pl.kosciukw.petsify.feature.signup.usecase.StartOtpRegistrationUseCase

val signUpModule = module {
    factoryOf(::StartOtpRegistrationUseCase)
    factoryOf(::FinalizeOtpRegistrationUseCase)
    factoryOf(::RefreshTokenUseCase)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignUpByOtpViewModel)
}
