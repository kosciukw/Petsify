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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val authDataStoreQualifier = named("AuthDataStore")

val authModule = module {
    single<DataStore<Preferences>>(qualifier = authDataStoreQualifier) {
        provideAuthPreferencesDataStore(androidContext())
    }
    single<TokenPersistence> {
        TokenPersistenceImpl(
            dataStore = get(authDataStoreQualifier)
        )
    }
    single<AuthSessionRepository> {
        AuthSessionRepositoryImpl(tokenPersistence = get())
    }
    single<AuthTokenService> {
        AuthTokenServiceImpl(
            authSessionRepository = get(),
            userApi = get(),
            userUrlProvider = get(),
            userExceptionMapper = get()
        )
    }
}

private fun provideAuthPreferencesDataStore(
    context: Context
): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    produceFile = { context.preferencesDataStoreFile("auth_prefs.preferences_pb") }
)
