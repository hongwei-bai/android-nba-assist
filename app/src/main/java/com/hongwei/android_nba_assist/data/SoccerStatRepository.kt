package com.hongwei.android_nba_assist.data

import com.hongwei.android_nba_assist.data.network.service.SoccerStatService
import com.hongwei.android_nba_assist.ui.component.DataStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class SoccerStatRepository @Inject constructor(
    private val soccerStatService: SoccerStatService
) {
    private val dataStatusChannel = Channel<DataStatus>()

    val dataStatus = dataStatusChannel.receiveAsFlow()
}