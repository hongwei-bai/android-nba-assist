package com.hongwei.android_nba_assist.redux

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.hongwei.android_nba_assist.redux.event.NavigationEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class NavigationReducer @Inject constructor() {
    var navHostController: NavHostController? = null

    fun register() {
        EventBus.getDefault().register(this)
    }

    fun unregister() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: NavigationEvent) {
        navHostController?.navigate(event.route)
    }
}