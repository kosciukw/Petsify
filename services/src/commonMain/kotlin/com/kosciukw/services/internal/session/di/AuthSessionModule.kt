package com.kosciukw.services.internal.session.di

import com.kosciukw.services.internal.session.repository.AuthSessionRepository
import com.kosciukw.services.internal.session.repository.impl.AuthSessionRepositoryImpl
import com.kosciukw.services.internal.session.service.AuthTokenService
import com.kosciukw.services.internal.session.service.impl.AuthTokenServiceImpl
import org.koin.dsl.module

val authSessionModule = module {
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
