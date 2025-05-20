package com.kosciukw.services.data.user.di

import com.kosciukw.services.data.user.api.UserApi
import com.kosciukw.services.data.user.api.controller.UserApiController
import com.kosciukw.services.data.user.api.controller.impl.UserApiControllerImpl
import com.kosciukw.services.data.user.api.provider.UserUrlProvider
import com.kosciukw.services.data.user.api.provider.impl.UserUrlProviderImpl
import com.kosciukw.services.data.user.error.mapper.ErrorResponseToUserApiExceptionMapper
import com.kosciukw.services.data.user.error.mapper.HttpToUserApiExceptionMapper
import com.kosciukw.services.data.user.error.mapper.UserExceptionMapper
import com.kosciukw.services.data.user.error.mapper.impl.ErrorResponseToUserApiExceptionMapperImpl
import com.kosciukw.services.data.user.error.mapper.impl.HttpToUserApiExceptionMapperImpl
import com.kosciukw.services.data.user.error.mapper.impl.UserExceptionMapperImpl
import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.UserApiToDomainErrorMapper
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
import okhttp3.ResponseBody
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        pairByPasswordDomainToRequestModelMapper: PairByPasswordDomainToRequestModelMapper
    ): UserRepository = UserRepositoryRemoteImpl(
        errorMapper = errorMapper,
        networkStateProvider = networkStateProvider,
        userApiController = userApiController,
        pairByPasswordDomainToRequestModelMapper = pairByPasswordDomainToRequestModelMapper
    )

    @Provides
    fun provideUserApiToDomainErrorMapper(): UserApiToDomainErrorMapper =
        UserApiToDomainErrorMapperImpl()

    @Provides
    @Singleton
    @Named("UserApiRetrofit")
    fun provideUserRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.33:8080/") //TODO 10.05.2025 ip is not constant
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(
        @Named("UserApiRetrofit") retrofit: Retrofit
    ): UserApi {
        return retrofit.create(UserApi::class.java)
    }

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
        userRepository: UserRepository
    ): UserService = UserServiceImpl(
        userRepository = userRepository
    )

    @Provides
    fun provideUserExceptionMapper(
        httpToUserApiExceptionMapper: HttpToUserApiExceptionMapper
    ): UserExceptionMapper = UserExceptionMapperImpl(
        httpToUserApiExceptionMapper = httpToUserApiExceptionMapper
    )

    @Provides
    fun provideHttpToUserApiExceptionMapper(
        httpExceptionToErrorResponseMapper: HttpExceptionToErrorResponseMapper,
        errorResponseToUserApiExceptionMapper: ErrorResponseToUserApiExceptionMapper
    ): HttpToUserApiExceptionMapper = HttpToUserApiExceptionMapperImpl(
        httpExceptionToErrorResponseMapper = httpExceptionToErrorResponseMapper,
        errorResponseToUserApiExceptionMapper = errorResponseToUserApiExceptionMapper
    )

    @Provides
    fun provideHttpExceptionToErrorResponseMapper(
        converter: Converter<ResponseBody, ErrorResponse>
    ): HttpExceptionToErrorResponseMapper = HttpExceptionToErrorResponseMapperImpl(
        retrofitErrorResponseBodyConverter = converter
    )

    @Provides
    fun provideErrorResponseConverter(
        @Named("UserApiRetrofit") retrofit: Retrofit
    ): Converter<ResponseBody, ErrorResponse> {
        return retrofit.responseBodyConverter(
            ErrorResponse::class.java,
            emptyArray()
        )
    }

    @Provides
    fun provideErrorResponseToUserApiExceptionMapper(): ErrorResponseToUserApiExceptionMapper =
        ErrorResponseToUserApiExceptionMapperImpl()
}