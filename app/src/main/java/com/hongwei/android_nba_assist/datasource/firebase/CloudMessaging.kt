package com.hongwei.android_nba_assist.datasource.firebase

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CloudMessaging @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun register() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("bbbb", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d("bbbb", "firebase cloud msg, token: $token")
        })
    }
}