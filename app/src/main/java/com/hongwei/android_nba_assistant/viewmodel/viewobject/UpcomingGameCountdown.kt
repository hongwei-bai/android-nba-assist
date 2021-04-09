package com.hongwei.android_nba_assistant.viewmodel.viewobject

data class UpcomingGameCountdown(
    val countdownCaption: CountdownCaption,
    val countdownStaticValue: Int = 0,
    val countdownUnit: CountdownUnit
)

enum class CountdownCaption {
    In, On
}

enum class CountdownUnit {
    Days, Hours, Countdown
}

enum class CountdownStatus {
    Inactive, Counting, CountdownZero, Now, Started, Today, Tomorrow
}

const val DISPLAY_HOURS_IN_HOURS = 8
const val DISPLAY_COUNTDOWN_IN_HOURS = 2
const val COUNTDOWN_ZERO_FREEZE_MILLIS = 10L
const val DISPLAY_STARTED_FROM_MINUTES = 5
const val IGNORE_TODAY_S_GAME_FROM_HOURS = 5
