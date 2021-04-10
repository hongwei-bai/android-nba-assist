package com.hongwei.android_nba_assistant.datasource.local

import javax.inject.Inject

class LocalSettings @Inject constructor() {
    val myTeam: String = "gsw"

    val scheduleWeeks: Int = 4

    val startsFromMonday: Boolean = true
}