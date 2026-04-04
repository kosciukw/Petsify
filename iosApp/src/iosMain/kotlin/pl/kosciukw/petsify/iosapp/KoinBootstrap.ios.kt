package pl.kosciukw.petsify.iosapp

import com.kosciukw.services.api.user.error.UserDomainError
import com.kosciukw.services.api.user.error.UserDomainToAppErrorMapper
import com.kosciukw.services.internal.session.di.authSessionModule
import com.kosciukw.services.internal.session.model.AuthTokens
import com.kosciukw.services.internal.session.persistence.TokenPersistence
import com.kosciukw.services.internal.user.di.userModule
import com.kosciukw.services.internal.user.mapper.di.servicesMappersModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatform
import pl.kosciukw.petsify.feature.login.di.loginModule
import pl.kosciukw.petsify.feature.main.di.mainModule
import pl.kosciukw.petsify.feature.settings.di.settingsModule
import pl.kosciukw.petsify.feature.signup.di.signUpModule
import pl.kosciukw.petsify.feature.splash.di.splashModule
import pl.kosciukw.petsify.shared.error.mapper.CoreDomainToAppErrorMapper
import pl.kosciukw.petsify.shared.error.mapper.IntegrationErrorMapper
import pl.kosciukw.petsify.shared.network.NetworkStateProvider
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidator
import pl.kosciukw.petsify.shared.validator.email.EmailIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.email.EmailValidator
import pl.kosciukw.petsify.shared.validator.email.EmailValidatorImpl
import pl.kosciukw.petsify.shared.validator.notempty.NotEmptyValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordIdentifierValidatorImpl
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordMatchValidatorImpl
import pl.kosciukw.petsify.shared.validator.password.PasswordValidator
import pl.kosciukw.petsify.shared.validator.password.PasswordValidatorImpl

internal actual fun initIosKoin() {
    if (runCatching { KoinPlatform.getKoin() }.getOrNull() != null) return

    startKoin {
        modules(
            iosSharedModule,
            iosValidatorsModule,
            iosErrorModule,
            iosAppModule,
            servicesMappersModule,
            authSessionModule,
            userModule,
            loginModule,
            mainModule,
            signUpModule,
            settingsModule,
            splashModule
        )
    }
}

private val iosSharedModule = module {
    single<NetworkStateProvider> { IosNetworkStateProvider() }
}

private val iosValidatorsModule = module {
    single<EmailValidator> { EmailValidatorImpl() }
    single<NotEmptyValidator<CharArray>> { NotEmptyValidator() }
    singleOf(::EmailIdentifierValidatorImpl) { bind<EmailIdentifierValidator>() }
    single<PasswordValidator> { PasswordValidatorImpl() }
    singleOf(::PasswordIdentifierValidatorImpl) { bind<PasswordIdentifierValidator>() }
    single<PasswordMatchValidator> { PasswordMatchValidatorImpl() }
}

private val iosErrorModule = module {
    single<CoreDomainToAppErrorMapper> { IosCoreDomainToAppErrorMapper() }
}

private val iosAppModule = module {
    single<UserDomainToAppErrorMapper> { IosUserDomainToAppErrorMapper() }
    single<TokenPersistence> { IosTokenPersistence() }
    singleOf(::IosIntegrationErrorMapper) { bind<IntegrationErrorMapper>() }
    single<FeatureStringsProvider> { IosFeatureStringsProvider() }
}
