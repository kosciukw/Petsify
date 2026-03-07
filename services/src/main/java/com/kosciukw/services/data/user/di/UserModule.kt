package com.kosciukw.services.data.user.di

import com.kosciukw.services.data.session.service.AuthTokenService
import com.kosciukw.services.data.user.api.UserApi
import com.kosciukw.services.data.user.api.authenticator.TokenAuthenticator
import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.api.controller.impl.UserApiControllerImpl
import com.kosciukw.services.data.user.api.interceptor.AuthInterceptor
import com.kosciukw.services.data.user.api.provider.UserUrlProvider
import com.kosciukw.services.data.user.api.provider.impl.UserUrlProviderImpl
import com.kosciukw.services.data.user.error.mapper.*
import com.kosciukw.services.data.user.error.mapper.impl.ErrorResponseToUserApiExceptionMapperImpl
import com.kosciukw.services.data.user.error.mapper.impl.HttpToUserApiExceptionMapperImpl
import com.kosciukw.services.data.user.error.mapper.impl.UserExceptionMapperImpl
import com.kosciukw.services.data.user.mapper.*
import com.kosciukw.services.data.user.repository.UserRepository
import com.kosciukw.services.data.user.repository.error.UserApiToDomainErrorMapperImpl
import com.kosciukw.services.data.user.repository.impl.UserRepositoryRemoteImpl
import com.kosciukw.services.data.user.service.user.UserService
import com.kosciukw.services.data.user.service.user.impl.UserServiceImpl
import com.kosciukw.services.error.ErrorResponse
import com.kosciukw.services.mapper.HttpExceptionToErrorResponseMapper
import com.kosciukw.services.mapper.impl.HttpExceptionToErrorResponseMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        errorMapper: UserApiToDomainErrorMapper,
        networkStateProvider: NetworkStateProvider,
        userApiController: UserApiController,
        loginByPasswordDomainToRequestModelMapper: LoginByPasswordDomainToRequestModelMapper,
        signUpDomainToRequestModelMapper: SignUpDomainToRequestModelMapper,
        startOtpRegistrationDomainToRequestModelMapper: StartOtpRegistrationDomainToRequestModelMapper,
        finalizeOtpRegistrationDomainToRequestModelMapper: FinalizeOtpRegistrationDomainToRequestModelMapper
    ): UserRepository = UserRepositoryRemoteImpl(
        errorMapper = errorMapper,
        networkStateProvider = networkStateProvider,
        userApiController = userApiController,
        loginByPasswordDomainToRequestModelMapper = loginByPasswordDomainToRequestModelMapper,
        signUpDomainToRequestModelMapper = signUpDomainToRequestModelMapper,
        startOtpRegistrationDomainToRequestModelMapper = startOtpRegistrationDomainToRequestModelMapper,
        finalizeOtpRegistrationDomainToRequestModelMapper = finalizeOtpRegistrationDomainToRequestModelMapper
    )

    @Provides
    fun provideUserApiToDomainErrorMapper(): UserApiToDomainErrorMapper =
        UserApiToDomainErrorMapperImpl()

    @Provides
    @Singleton
    @Named("UserApiOkHttp")
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BASIC
//        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            //  .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("UserApiRetrofit")
    fun provideUserRetrofit(
        @Named("UserApiOkHttp") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.100.137:8080/") // TODO ip nie jest stałe
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(
        @Named("UserApiRetrofit") retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun provideUserApiController(
        userApi: UserApi,
        userUrlProvider: UserUrlProvider,
        userExceptionMapper: UserExceptionMapper
    ): UserApiController = UserApiControllerImpl(
        userApi = userApi,
        userUrlProvider = userUrlProvider,
        userExceptionMapper = userExceptionMapper
    )

    @Provides
    fun provideUserUrlProvider(): UserUrlProvider = UserUrlProviderImpl()

    @Provides
    fun provideUserService(
        userRepository: UserRepository,
        authTokenService: AuthTokenService
    ): UserService = UserServiceImpl(
        userRepository = userRepository,
        authTokenService = authTokenService
    )

    @Provides
    fun provideUserExceptionMapper(
        httpToUserApiExceptionMapper: HttpToUserApiExceptionMapper
    ): UserExceptionMapper = UserExceptionMapperImpl(httpToUserApiExceptionMapper)

    @Provides
    fun provideHttpToUserApiExceptionMapper(
        httpExceptionToErrorResponseMapper: HttpExceptionToErrorResponseMapper,
        errorResponseToUserApiExceptionMapper: ErrorResponseToUserApiExceptionMapper
    ): HttpToUserApiExceptionMapper = HttpToUserApiExceptionMapperImpl(
        httpExceptionToErrorResponseMapper,
        errorResponseToUserApiExceptionMapper
    )

    @Provides
    fun provideHttpExceptionToErrorResponseMapper(
        converter: Converter<ResponseBody, ErrorResponse>
    ): HttpExceptionToErrorResponseMapper = HttpExceptionToErrorResponseMapperImpl(converter)

    @Provides
    fun provideErrorResponseConverter(
        @Named("UserApiRetrofit") retrofit: Retrofit
    ): Converter<ResponseBody, ErrorResponse> =
        retrofit.responseBodyConverter(ErrorResponse::class.java, emptyArray())

    @Provides
    fun provideErrorResponseToUserApiExceptionMapper(): ErrorResponseToUserApiExceptionMapper =
        ErrorResponseToUserApiExceptionMapperImpl()
}