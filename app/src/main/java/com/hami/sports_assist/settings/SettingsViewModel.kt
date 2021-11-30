package com.hami.sports_assist.settings

import android.content.Context
import androidx.lifecycle.*
import com.hami.sports_assist.ExceptionHelper.nbaExceptionHandler
import com.hami.sports_assist.data.NbaStatRepository
import com.hami.sports_assist.data.NbaTeamRepository
import com.hami.sports_assist.data.local.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val teamBanner: LiveData<String> =
        nbaTeamRepository.getTeamDetailFlow()
            .map { it.bannerUrl }
            .asLiveData(viewModelScope.coroutineContext)

    val scheduleWeeksSettingChanged: MutableLiveData<Boolean> = MutableLiveData()

    val startFromMondaySettingChanged: MutableLiveData<Boolean> = MutableLiveData()

    init {
        scheduleWeeksSettingChanged.value = AppSettings.hasScheduleWeeksSettingChanged()
        startFromMondaySettingChanged.value = AppSettings.hasWeekStartFromMondaySettingChanged()
    }

    fun switchTeam(context: Context, team: String) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            AppSettings.setNbaTeam(context, team)
            reloadAll()
        }
    }

    fun setScheduleWeeks(context: Context, weeks: Int) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            AppSettings.setScheduleWeeks(context, weeks)
            scheduleWeeksSettingChanged.postValue(AppSettings.hasScheduleWeeksSettingChanged())
        }
    }

    fun setWeekStartFromMonday(context: Context, isStartFromMonday: Boolean) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            AppSettings.setStartFromMonday(context, isStartFromMonday)
            startFromMondaySettingChanged.postValue(AppSettings.hasWeekStartFromMondaySettingChanged())
        }
    }

    private suspend fun reloadAll() {
        nbaTeamRepository.fetchTeamDetailFromBackend(AppSettings.myNbaTeam)
        nbaTeamRepository.fetchSeasonStatusFromBackend()
        nbaStatRepository.fetchTeamScheduleFromBackend(AppSettings.myNbaTeam)
        nbaStatRepository.fetchStandingFromBackend()
        nbaStatRepository.fetchPostSeasonFromBackend()
    }
}