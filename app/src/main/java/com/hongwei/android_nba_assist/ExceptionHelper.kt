package com.hongwei.android_nba_assist

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHelper  {
    var postHandler: (() -> Unit)? = null

    val nbaExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (LocalProperties.isDebug) {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
            Log.e("bbbb", Log.getStackTraceString(throwable))
        } else {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
        }
        postHandler?.invoke()
    }
}