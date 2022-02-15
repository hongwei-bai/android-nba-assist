package com.hami.sports_assist

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHelper  {
    var postHandler: (() -> Unit)? = null

    val nbaExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
            Log.e("bbbb", Log.getStackTraceString(throwable))
        } else {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
        }
        postHandler?.invoke()
    }
}