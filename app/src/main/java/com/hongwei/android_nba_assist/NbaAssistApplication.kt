package com.hongwei.android_nba_assist

import android.app.Application
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NbaAssistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppSettings.initialize(this)
    }
}