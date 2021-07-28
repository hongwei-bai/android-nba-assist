package com.hongwei.android_nba_assist

import androidx.multidex.MultiDexApplication
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NbaAssistApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppSettings.initialize(this)
    }
}