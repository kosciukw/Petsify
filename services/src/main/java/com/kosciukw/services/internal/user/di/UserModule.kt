package com.kosciukw.services.internal.user.di

import com.kosciukw.services.api.auth.AuthService
import com.kosciukw.services.api.registration.RegistrationService
import com.kosciukw.services.api.session.SessionService
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.api.authenticator.TokenAuthenticator
import com.kosciukw.services.internal.user.api.controller.UserApiController
import com.kosciukw.services.internal.user.api.controller.impl.UserApiControllerImpl
import com.kosciukw.services.internal.user.api.interceptor.AuthInterceptor
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
import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.internal.user.service.UserServiceImpl
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import com.kosciukw.services.mapper.impl.HttpExceptionToErrorResponseMapperImpl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val userApiOkHttpQualifier = named("UserApiOkHttp")
private val userApiRetrofitQualifier = named("UserApiRetrofit")

val userModule = module {
    single<UserApiToDomainErrorMapper> { UserApiToDomainErrorMapperImpl() }
    single { AuthInterceptor(get()) }
    single { TokenAuthenticator(get()) }
    single<OkHttpClient>(qualifier = userApiOkHttpQualifier) {
        provideOkHttpClient(
            authInterceptor = get(),
            tokenAuthenticator = get()
        )
    }
    single<Retrofit>(qualifier = userApiRetrofitQualifier) {
        provideUserRetrofit(
            client = get(userApiOkHttpQualifier)
        )
    }
    single<UserApi> { get<Retrofit>(userApiRetrofitQualifier).create(UserApi::class.java) }
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
    single<HttpExceptionToErrorResponseMapper> { HttpExceptionToErrorResponseMapperImpl(get()) }
    single<Converter<ResponseBody, ErrorResponse>> {
        get<Retrofit>(userApiRetrofitQualifier).responseBodyConverter(ErrorResponse::class.java, emptyArray())
    }
    single<ErrorResponseToUserApiExceptionMapper> { ErrorResponseToUserApiExceptionMapperImpl() }
}

private fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
    tokenAuthenticator: TokenAuthenticator
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .authenticator(tokenAuthenticator)
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .build()

private fun provideUserRetrofit(
    client: OkHttpClient
): Retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/") // TODO ip nie jest stałe
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
