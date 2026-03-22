package pl.kosciukw.petsify.app

import android.app.Application
import com.kosciukw.services.internal.session.di.authSessionModule
import com.kosciukw.services.internal.user.di.userModule
import com.kosciukw.services.internal.user.mapper.di.servicesMappersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.kosciukw.petsify.di.authModule
import pl.kosciukw.petsify.di.appModule
import pl.kosciukw.petsify.feature.login.di.loginModule
import pl.kosciukw.petsify.feature.settings.di.settingsModule
import pl.kosciukw.petsify.feature.signup.di.signUpModule
import pl.kosciukw.petsify.feature.splash.di.splashModule
import pl.kosciukw.petsify.shared.di.sharedModule
import pl.kosciukw.petsify.shared.error.di.sharedErrorModule
import pl.kosciukw.petsify.shared.validator.di.validatorsModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                sharedModule,
                sharedErrorModule,
                validatorsModule,
                servicesMappersModule,
                authModule,
                authSessionModule,
                userModule,
                appModule,
                loginModule,
                signUpModule,
                settingsModule,
                splashModule
            )
        }
    }
}
