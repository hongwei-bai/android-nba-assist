package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hongwei.android_nba_assist.redux.event.NbaTeamSwitchEvent
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@HiltViewModel
class NbaTeamViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val team: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val teamBannerUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun start() {
        EventBus.getDefault().register(this)
        team.value = "gs00"
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: NbaTeamSwitchEvent) {
        team.value = event.team
        teamBannerUrl.value = nbaTeamRepository.getTeamBannerUrl(event.team)
    }

    override fun onCleared() {
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}