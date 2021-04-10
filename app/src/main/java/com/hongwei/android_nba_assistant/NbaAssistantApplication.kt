package com.hongwei.android_nba_assistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NbaAssistantApplication : Application() {
    var initialiseFlag = true
}