package com.hongwei.android_nba_assist.dashboard

import android.os.CountDownTimer
import com.hongwei.android_nba_assist.AppConfigurations.TeamScheduleConfiguration.COUNTDOWN_ZERO_FREEZE_MILLIS
import com.hongwei.android_nba_assist.AppConfigurations.TeamScheduleConfiguration.DISPLAY_STARTED_FROM_MINUTES
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.MILLIS_PER_MINUTE
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.MILLIS_PER_SECOND
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingGameCounter @Inject constructor() {
    private var countDownTimer: CountDownTimer? = null

    lateinit var countdownCallback: (String?) -> Unit

    fun startCountUp(coroutineScope: CoroutineScope, eventMillis: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            stopCountDown()
            val msDiff = DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE + eventMillis - System.currentTimeMillis()
            countDownTimer = object : CountDownTimer(msDiff, MILLIS_PER_MINUTE) {
                override fun onTick(millisUntilFinished: Long) {
                    val started = System.currentTimeMillis() - eventMillis
                    val displaying = when {
                        started > DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE -> {
                            StringBuilder().apply {
                                append(String.format("%d:", started / MILLIS_PER_HOUR))
                                append(String.format("%02d", (started % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE))
                            }.toString()
                        }
                        started > COUNTDOWN_ZERO_FREEZE_MILLIS -> {
                            "Now"
                        }
                        else -> {
                            "0:00:00"
                        }
                    }
                    countdownCallback.invoke(displaying)
                }

                override fun onFinish() {
                    countdownCallback.invoke("")
                }
            }.start()
        }
    }

    fun startCountDown(coroutineScope: CoroutineScope, eventMillis: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            stopCountDown()
            val msDiff = eventMillis - System.currentTimeMillis()
            countDownTimer = object : CountDownTimer(msDiff, MILLIS_PER_SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    countdownCallback.invoke(StringBuilder().apply {
                        append(String.format("%d:", millisUntilFinished / MILLIS_PER_HOUR))
                        append(String.format("%02d:", (millisUntilFinished % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE))
                        append(String.format("%02d", (millisUntilFinished % MILLIS_PER_MINUTE) / MILLIS_PER_SECOND))
                    }.toString())
                }

                override fun onFinish() {
                    coroutineScope.launch(Dispatchers.Main) {
                        countdownCallback.invoke("0:00:00")
                        delay(COUNTDOWN_ZERO_FREEZE_MILLIS)
                        countdownCallback.invoke("Now")
                        startCountUp(coroutineScope, eventMillis)
                    }
                }
            }.start()
        }
    }

    private fun stopCountDown() {
        countDownTimer?.cancel()
        countDownTimer = null
        countdownCallback.invoke("Now")
    }
}