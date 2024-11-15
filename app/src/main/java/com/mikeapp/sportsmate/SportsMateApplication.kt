package com.mikeapp.sportsmate

import android.app.Application
import com.mikeapp.sportsmate.data.local.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SportsMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppSettings.initialize(this)
    }
}