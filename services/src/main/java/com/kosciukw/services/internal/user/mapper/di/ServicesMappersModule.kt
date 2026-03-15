package com.kosciukw.services.internal.user.mapper.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import com.kosciukw.services.internal.user.mapper.FinalizeOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.StartOtpRegistrationDomainToRequestModelMapper
import com.kosciukw.services.internal.user.mapper.AccessTokenApiToAuthSessionDomainModelMapper
import com.kosciukw.services.internal.user.mapper.impl.AccessTokenApiToAuthSessionDomainModelMapperImpl
import com.kosciukw.services.internal.user.mapper.impl.LoginByPasswordDomainToRequestModelMapperImpl
import com.kosciukw.services.internal.user.mapper.impl.StartOtpRegistrationDomainToRequestModelMapperImpl
import org.koin.dsl.module

val servicesMappersModule = module {
    singleOf(::AccessTokenApiToAuthSessionDomainModelMapperImpl) { bind<AccessTokenApiToAuthSessionDomainModelMapper>() }
    singleOf(::LoginByPasswordDomainToRequestModelMapperImpl) { bind<LoginByPasswordDomainToRequestModelMapper>() }
    singleOf(::StartOtpRegistrationDomainToRequestModelMapperImpl) { bind<StartOtpRegistrationDomainToRequestModelMapper>() }
    singleOf(::FinalizeOtpRegistrationDomainToRequestModelMapperImpl) { bind<FinalizeOtpRegistrationDomainToRequestModelMapper>() }
}
