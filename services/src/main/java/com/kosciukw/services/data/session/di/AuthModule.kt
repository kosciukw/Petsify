package com.kosciukw.services.internal.session.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import com.kosciukw.services.internal.session.repository.impl.AuthSessionRepositoryImpl
import com.kosciukw.services.internal.session.persistence.TokenPersistence
import com.kosciukw.services.internal.session.persistence.impl.TokenPersistenceImpl
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.session.service.impl.AuthTokenServiceImpl
import com.kosciukw.services.internal.user.api.UserApi
import com.kosciukw.services.internal.user.api.provider.UserUrlProvider
import com.kosciukw.services.internal.user.error.mapper.UserExceptionMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthTokenStore

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    @AuthTokenStore
    fun provideTokenPersistence(
        @AuthDataStore dataStore: DataStore<Preferences>
    ): TokenPersistence = TokenPersistenceImpl(
        dataStore = dataStore
    )

    @Provides
    @Singleton
    fun provideAuthSessionRepository(
        @AuthTokenStore tokenPersistence: TokenPersistence
    ): AuthSessionRepository = AuthSessionRepositoryImpl(tokenPersistence = tokenPersistence)

    @Provides
    @Singleton
    @AuthDataStore
    fun provideAuthPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
        produceFile = { context.preferencesDataStoreFile("auth_prefs.preferences_pb") }
    )

    @Provides
    @Singleton
    fun provideAuthTokenService(
        authSessionRepository: AuthSessionRepository,
        userApi: UserApi,
        userUrlProvider: UserUrlProvider,
        userExceptionMapper: UserExceptionMapper
    ): AuthTokenService = AuthTokenServiceImpl(
        authSessionRepository = authSessionRepository,
        userApi = userApi,
        userUrlProvider = userUrlProvider,
        userExceptionMapper = userExceptionMapper
    )
}
