package com.hongwei.android_nba_assist.datasource.local

import javax.inject.Inject

class LocalSettings @Inject constructor() {
    val myTeam: String = "gs"

    val conference: Conference = Conference.Western

    val scheduleWeeks: Int = 4

    val startsFromMonday: Boolean = true
}

enum class Conference {
    Eastern, Western
}