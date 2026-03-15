package com.kosciukw.services.internal.user.error.di

import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import com.kosciukw.services.internal.user.error.impl.UserDomainToAppErrorMapperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userServiceModule = module {
    single<UserDomainToAppErrorMapper> { UserDomainToAppErrorMapperImpl(androidContext()) }
}
