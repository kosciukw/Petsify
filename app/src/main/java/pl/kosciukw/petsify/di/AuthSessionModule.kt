package pl.kosciukw.petsify.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.kosciukw.services.internal.session.persistence.TokenPersistence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.kosciukw.petsify.persistence.TokenPersistenceImpl

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
}

private fun provideAuthPreferencesDataStore(
    context: Context
): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    produceFile = { context.preferencesDataStoreFile("auth_prefs.preferences_pb") }
)
