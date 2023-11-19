package com.hulkdx.findprofessional

import android.app.Application
import com.hulkdx.findprofessional.common.di.initKoin
import com.hulkdx.findprofessional.di.appModule
import com.hulkdx.findprofessional.feature.authentication.login.loginModule
import com.hulkdx.findprofessional.feature.authentication.signup.signUpModule
import com.hulkdx.findprofessional.feature.authentication.splash.splashModule
import com.hulkdx.findprofessional.feature.developer.developerModule
import com.hulkdx.findprofessional.feature.home.detail.homeDetailModule
import com.hulkdx.findprofessional.feature.home.homeModule
import com.hulkdx.findprofessional.feature.profile.profileModule
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("Saba MainApplication onCreate")

        initKoin {
            androidContext(this@MainApplication)
            modules(
                appModule,
                loginModule,
                signUpModule,
                homeModule,
                homeDetailModule,
                developerModule,
                splashModule,
                profileModule,
            )
        }

        debugOnCreate()
    }
}
