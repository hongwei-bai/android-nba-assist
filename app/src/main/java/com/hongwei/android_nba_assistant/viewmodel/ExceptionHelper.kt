package com.hongwei.android_nba_assistant.viewmodel

import android.util.Log
import com.hongwei.android_nba_assistant.LocalProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class ExceptionHelper @Inject constructor() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        if (LocalProperties.isDebug) {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
            Log.e("bbbb", Log.getStackTraceString(throwable))
        } else {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
        }
    }
}