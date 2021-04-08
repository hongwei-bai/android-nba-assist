package com.hongwei.android_nba_assistant.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assistant.model.LocalSettings
import com.hongwei.android_nba_assistant.usecase.WarriorsCalendarUseCase
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_MINUTE
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_SECOND
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getBeginOfDay
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.isFuture
import com.hongwei.android_nba_assistant.viewmodel.viewobject.CountDownStatus
import com.hongwei.android_nba_assistant.viewmodel.viewobject.InDaysCaption
import com.hongwei.android_nba_assistant.viewmodel.viewobject.InDaysUnit
import com.hongwei.android_nba_assistant.viewmodel.viewobject.UpcomingGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val warriorsCalendarUseCase: WarriorsCalendarUseCase,
    private val localSettings: LocalSettings,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {
    val loadingStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val upcomingGame: MutableLiveData<UpcomingGame?> by lazy {
        MutableLiveData<UpcomingGame?>()
    }
    val countDownString: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
    val countDownStatus: MutableLiveData<CountDownStatus> by lazy {
        MutableLiveData<CountDownStatus>()
    }

    private var countDownTimer: CountDownTimer? = null

    fun load() {
        loadingStatus.value = true
        countDownStatus.value = CountDownStatus.None
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            delay(1)
            val scheduleUpcoming = warriorsCalendarUseCase.getWarriorsSchedule().filter {
                it.date.after(getBeginOfDay(Calendar.getInstance()))
            }
            val upcomingOne = scheduleUpcoming.firstOrNull()
            launch(Dispatchers.Main) {
                loadingStatus.value = false
                upcomingGame.value = upcomingOne?.run {
                    //debug
//                    date = Calendar.getInstance().apply {
//                        set(2021, Calendar.APRIL, 9, 0, 24, 45)
//                    }

                    var unit = InDaysUnit.Days
                    val inDays = LocalDateTimeUtil.getInDays(date)
                    val inHours = if (inDays == 0) {
                        LocalDateTimeUtil.getInHours(date)
                    } else 24
                    if (!isFuture(date)) {
                        unit = InDaysUnit.Countdown
                        countDownStatus.value = CountDownStatus.Now
                    } else if (inHours <= 2) {
                        unit = InDaysUnit.Countdown
                        startCountDown(date.timeInMillis - Calendar.getInstance().timeInMillis)
                    } else if (inHours < 8) {
                        unit = InDaysUnit.Hours
                    }

                    UpcomingGame(
                        homeTeamShort = if (isHome) localSettings.myTeam else teamShort,
                        guestTeamShort = if (isHome) teamShort else localSettings.myTeam,
                        dateString = LocalDateTimeUtil.getLocalDateDisplay(date),
                        timeString = LocalDateTimeUtil.getLocalTimeDisplay(date),
                        inDaysCaption = getInDaysCaption(inDays, unit),
                        inDaysValue = if (unit == InDaysUnit.Days) inDays else inHours,
                        inDaysUnit = unit,
                        gamesLeft = scheduleUpcoming.size
                    )
                }
            }
        }
    }

    private fun startCountDown(msDiff: Long) {
        stopCountDown()
        countDownTimer = object : CountDownTimer(msDiff, MILLIS_PER_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                countDownString.value = StringBuilder().apply {
                    append(String.format("%d:", millisUntilFinished / MILLIS_PER_HOUR))
                    append(String.format("%02d:", (millisUntilFinished % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE))
                    append(String.format("%02d", (millisUntilFinished % MILLIS_PER_MINUTE) / MILLIS_PER_SECOND))
                }.toString()
            }

            override fun onFinish() {
                viewModelScope.launch {
                    countDownString.value = null
                    countDownStatus.value = CountDownStatus.CountdownZero
                    delay(10)
                    countDownStatus.value = CountDownStatus.Now
                }
            }
        }.start()
    }

    private fun stopCountDown() {
        countDownTimer?.cancel()
        countDownTimer = null
        countDownString.value = null
        countDownStatus.value = CountDownStatus.Now
    }

    private fun getInDaysCaption(inDaysValue: Int, inDaysUnit: InDaysUnit): InDaysCaption =
        if (inDaysUnit == InDaysUnit.Days && inDaysValue <= 2) {
            InDaysCaption.On
        } else {
            InDaysCaption.In
        }
}