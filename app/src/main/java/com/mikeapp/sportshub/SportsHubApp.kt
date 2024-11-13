package com.mikeapp.sportshub

import android.app.Application
import com.mikeapp.sportshub.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SportsHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SportsHubApp)
            modules(appModule)
        }
    }
}