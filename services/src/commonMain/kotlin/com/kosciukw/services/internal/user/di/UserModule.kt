package com.kosciukw.services.internal.user.di

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.registration.RegistrationService
import com.kosciukw.services.api.session.SessionService
import com.kosciukw.services.internal.user.api.creator.createPlatformHttpClient
import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.api.controller.UserApiController
import com.kosciukw.services.internal.user.api.controller.impl.UserApiControllerImpl
import com.kosciukw.services.internal.user.api.impl.UserApiImpl
import com.kosciukw.services.internal.user.api.interceptor.AuthInterceptor
import com.kosciukw.services.internal.user.api.interceptor.AuthRequestRetrier
import com.kosciukw.services.internal.user.api.provider.UserUrlProvider
import com.kosciukw.services.internal.user.api.provider.impl.UserUrlProviderImpl
import com.kosciukw.services.internal.user.error.mapper.*
import com.kosciukw.services.internal.user.error.mapper.impl.ErrorResponseToUserApiExceptionMapperImpl
import com.kosciukw.services.internal.user.error.mapper.impl.HttpToUserApiExceptionMapperImpl
import com.kosciukw.services.internal.user.error.mapper.impl.UserExceptionMapperImpl
import com.kosciukw.services.internal.user.mapper.*
import com.kosciukw.services.internal.user.repository.UserRepository
import com.kosciukw.services.internal.user.repository.error.UserApiToDomainErrorMapperImpl
import com.kosciukw.services.internal.user.repository.impl.UserRepositoryRemoteImpl
import com.kosciukw.services.internal.user.service.UserServiceImpl
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import com.kosciukw.services.mapper.impl.HttpExceptionToErrorResponseMapperImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.kosciukw.petsify.shared.network.NetworkStateProvider

private val userApiHttpClientQualifier = named("UserApiHttpClient")

val userModule = module {
    single<UserApiToDomainErrorMapper> { UserApiToDomainErrorMapperImpl() }
    single { AuthInterceptor(get()) }
    single { AuthRequestRetrier(get()) }
    single<HttpClient>(qualifier = userApiHttpClientQualifier) {
        createPlatformHttpClient(
            authInterceptor = get(),
            authRequestRetrier = get()
        )
    }
    single<UserApi> { UserApiImpl(httpClient = get(userApiHttpClientQualifier)) }
    single<UserApiController> { UserApiControllerImpl(get(), get(), get()) }
    single<UserUrlProvider> { UserUrlProviderImpl() }
    single<UserRepository> {
        UserRepositoryRemoteImpl(
            loginByPasswordDomainToRequestModelMapper = get(),
            startOtpRegistrationDomainToRequestModelMapper = get(),
            finalizeOtpRegistrationDomainToRequestModelMapper = get(),
            networkStateProvider = get<NetworkStateProvider>(),
            errorMapper = get(),
            userApiController = get()
        )
    }
    single { UserServiceImpl(get(), get(), get()) }
    single<AuthService> { get<UserServiceImpl>() }
    single<RegistrationService> { get<UserServiceImpl>() }
    single<SessionService> { get<UserServiceImpl>() }
    single<UserExceptionMapper> { UserExceptionMapperImpl(get()) }
    single<HttpToUserApiExceptionMapper> { HttpToUserApiExceptionMapperImpl(get(), get()) }
    single<HttpExceptionToErrorResponseMapper> { HttpExceptionToErrorResponseMapperImpl() }
    single<ErrorResponseToUserApiExceptionMapper> { ErrorResponseToUserApiExceptionMapperImpl() }
}
