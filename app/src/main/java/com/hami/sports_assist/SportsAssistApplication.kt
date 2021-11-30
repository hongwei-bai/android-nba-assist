package com.hami.sports_assist

import androidx.multidex.MultiDexApplication
import com.hami.sports_assist.data.local.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SportsAssistApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppSettings.initialize(this)
    }
}