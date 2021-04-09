package com.hongwei.android_nba_assistant.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.usecase.MatchEvent
import com.hongwei.android_nba_assistant.usecase.TeamScheduleUseCase
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_MINUTE
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_SECOND
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assistant.viewmodel.viewobject.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val teamScheduleUseCase: TeamScheduleUseCase,
    private val localSettings: LocalSettings,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {
    val loadingStatus: MutableLiveData<LoadingStatus> by lazy {
        MutableLiveData<LoadingStatus>()
    }
    val gamesLeft: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val upcomingGameInfo: MutableLiveData<UpcomingGameInfo> by lazy {
        MutableLiveData<UpcomingGameInfo>()
    }
    val upcomingGameCountdown: MutableLiveData<UpcomingGameCountdown> by lazy {
        MutableLiveData<UpcomingGameCountdown>()
    }
    val countdownDynamicValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val countdownStatus: MutableLiveData<CountdownStatus> by lazy {
        MutableLiveData<CountdownStatus>()
    }

    private var countDownTimer: CountDownTimer? = null

    init {
        exceptionHelper.postHandler = {
            viewModelScope.launch(Dispatchers.Main) { loadingStatus.value = LoadingStatus.Error }
        }
    }

    fun load() {
        loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            delay(1)
            val teamSchedule = teamScheduleUseCase.getTeamSchedule(localSettings.myTeam)
            val scheduleUpcoming = teamSchedule.filter {
                it.date.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
            }
            val upcomingOne = scheduleUpcoming.firstOrNull()
            viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
                loadingStatus.value = LoadingStatus.Inactive
                gamesLeft.value = scheduleUpcoming.size
                upcomingOne?.run {
                    upcomingGameInfo.value = mapToGameInfoViewObject()
                    handleGameCountdown()
                }
            }
        }
    }

    private fun MatchEvent.handleGameCountdown() {
        var unit: CountdownUnit = CountdownUnit.Days
        var staticValue = 0
        var caption: CountdownCaption = CountdownCaption.In
        var status: CountdownStatus = CountdownStatus.Inactive

        when (val inMillis = LocalDateTimeUtil.getInMillis(date)) {
            in (DISPLAY_HOURS_IN_HOURS * MILLIS_PER_HOUR)..Long.MAX_VALUE -> {
                unit = CountdownUnit.Days
                staticValue = LocalDateTimeUtil.getInDays(date)
                when (staticValue) {
                    0 -> status = CountdownStatus.Today
                    1 -> status = CountdownStatus.Tomorrow
                    else -> {
                        //No-Op
                    }
                }
                if (staticValue <= 2) {
                    caption = CountdownCaption.On
                }
            }
            in (DISPLAY_COUNTDOWN_IN_HOURS * MILLIS_PER_HOUR)..(DISPLAY_HOURS_IN_HOURS * MILLIS_PER_HOUR) -> {
                unit = CountdownUnit.Hours
                staticValue = LocalDateTimeUtil.getInHours(date)
            }
            in 0..(DISPLAY_COUNTDOWN_IN_HOURS * MILLIS_PER_HOUR) -> {
                unit = CountdownUnit.Countdown
                status = CountdownStatus.Counting
                startCountDown(inMillis)
            }
            in (-DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE)..0 -> {
                unit = CountdownUnit.Countdown
                status = CountdownStatus.Now
            }
            in (-IGNORE_TODAY_S_GAME_FROM_HOURS * MILLIS_PER_HOUR)..(-DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE) -> {
                unit = CountdownUnit.Countdown
                status = CountdownStatus.Started
                startCountUp(-inMillis, date.timeInMillis)
            }
            else -> {
                status = CountdownStatus.Inactive
            }
        }

        upcomingGameCountdown.value = UpcomingGameCountdown(caption, staticValue, unit)
        countdownStatus.value = status
    }

    private fun MatchEvent.mapToGameInfoViewObject(): UpcomingGameInfo = UpcomingGameInfo(
        homeTeamShort = if (isHome) localSettings.myTeam else teamShort,
        guestTeamShort = if (isHome) teamShort else localSettings.myTeam,
        dateString = LocalDateTimeUtil.getLocalDateDisplay(date),
        timeString = LocalDateTimeUtil.getLocalTimeDisplay(date),
    )

    private fun startCountUp(msDiff: Long, referenceMillis: Long) {
        stopCountDown()
        countDownTimer = object : CountDownTimer(msDiff, MILLIS_PER_MINUTE) {
            override fun onTick(millisUntilFinished: Long) {
                val started = System.currentTimeMillis() - referenceMillis
                countdownDynamicValue.value = StringBuilder().apply {
                    append(String.format("%d:", started / MILLIS_PER_HOUR))
                    append(String.format("%02d", (started % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE))
                }.toString()
            }

            override fun onFinish() {
                viewModelScope.launch {
                    countdownStatus.value = CountdownStatus.Inactive
                }
            }
        }.start()
    }

    private fun startCountDown(msDiff: Long) {
        stopCountDown()
        countDownTimer = object : CountDownTimer(msDiff, MILLIS_PER_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                countdownDynamicValue.value = StringBuilder().apply {
                    append(String.format("%d:", millisUntilFinished / MILLIS_PER_HOUR))
                    append(String.format("%02d:", (millisUntilFinished % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE))
                    append(String.format("%02d", (millisUntilFinished % MILLIS_PER_MINUTE) / MILLIS_PER_SECOND))
                }.toString()
            }

            override fun onFinish() {
                viewModelScope.launch {
                    countdownStatus.value = CountdownStatus.CountdownZero
                    delay(COUNTDOWN_ZERO_FREEZE_MILLIS)
                    countdownStatus.value = CountdownStatus.Now
                }
            }
        }.start()
    }

    private fun stopCountDown() {
        countDownTimer?.cancel()
        countDownTimer = null
        countdownStatus.value = CountdownStatus.Now
    }
}