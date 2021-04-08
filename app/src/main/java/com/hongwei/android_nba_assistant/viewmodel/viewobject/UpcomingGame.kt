package com.hongwei.android_nba_assistant.viewmodel.viewobject

data class UpcomingGame(
    val homeTeamShort: String,
    val guestTeamShort: String,
    val dateString: String,
    val timeString: String,
    val inDaysCaption: InDaysCaption,
    val inDaysValue: Int,
    val inDaysUnit: InDaysUnit,
    val gamesLeft: Int
)

enum class InDaysCaption {
    In, On
}

enum class InDaysUnit {
    Days, Hours, Countdown
}

enum class CountDownStatus {
    None, CountdownZero, Now
}