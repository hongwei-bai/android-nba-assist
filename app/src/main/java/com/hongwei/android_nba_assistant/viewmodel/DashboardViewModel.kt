package com.hongwei.android_nba_assistant.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assistant.NbaAssistantApplication
import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.COUNTDOWN_ZERO_FREEZE_MILLIS
import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.DISPLAY_COUNTDOWN_IN_HOURS
import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.DISPLAY_HOURS_IN_HOURS
import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.DISPLAY_STARTED_FROM_MINUTES
import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.usecase.*
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_MINUTE
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.MILLIS_PER_SECOND
import com.hongwei.android_nba_assistant.viewmodel.viewobject.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val forceRequestScheduleUseCase: ForceRequestScheduleUseCase,
    private val upcomingGameUseCase: UpcomingGameUseCase,
    private val teamThemeUseCase: TeamThemeUseCase,
    private val teamLogoUseCase: TeamLogoUseCase,
    private val localSettings: LocalSettings,
    private val exceptionHelper: ExceptionHelper,
    @ApplicationContext private val applicationContext: Context
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
    val teamTheme: MutableLiveData<TeamTheme> by lazy {
        MutableLiveData<TeamTheme>()
    }

    private var countDownTimer: CountDownTimer? = null

    init {
        exceptionHelper.postHandler = {
            viewModelScope.launch(Dispatchers.Main) { loadingStatus.value = LoadingStatus.Error }
        }
        viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
            teamTheme.value = teamThemeUseCase.getTeamTheme()
        }
    }

    fun load() {
        loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            withContext(Dispatchers.IO) {
                forceRequestScheduleUseCase.forceRequestScheduleFromServer()
                (applicationContext as NbaAssistantApplication).initialiseFlag = false
            }
            loadCache()
        }
    }

    fun loadCache() {
        viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
            loadingStatus.value = LoadingStatus.Loading
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            val upcomingGame = upcomingGameUseCase.getUpcomingGame()
            val numberOfGamesLeft = upcomingGameUseCase.getNumberOfGamesLeft()
            viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
                loadingStatus.value = LoadingStatus.Inactive
                gamesLeft.value = numberOfGamesLeft
                upcomingGame?.run {
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

    private fun MatchEvent.mapToGameInfoViewObject(): UpcomingGameInfo {
        val homeTeam = if (isHome) localSettings.myTeam else opponentAbbrev
        val guestTeam = if (isHome) opponentAbbrev else localSettings.myTeam
        return UpcomingGameInfo(
            homeTeamLogoUrl = teamLogoUseCase.getTeamLogoUrl(homeTeam),
            guestTeamLogoUrl = teamLogoUseCase.getTeamLogoUrl(guestTeam),
            homeTeamLogoPlaceholder = teamLogoUseCase.getTeamLogoPlaceholder(homeTeam),
            guestTeamLogoPlaceholder = teamLogoUseCase.getTeamLogoPlaceholder(guestTeam),
            dateString = LocalDateTimeUtil.getLocalDateDisplay(date),
            timeString = LocalDateTimeUtil.getLocalTimeDisplay(date),
        )
    }

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