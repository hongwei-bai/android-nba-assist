package com.hongwei.android_nba_assist.datasource.local

import javax.inject.Inject

class LocalSettings @Inject constructor() {
    var myTeam: String = "gs"

    val conference: Conference = Conference.Western

    val scheduleWeeks: Int = 2

    val startsFromMonday: Boolean = true
}