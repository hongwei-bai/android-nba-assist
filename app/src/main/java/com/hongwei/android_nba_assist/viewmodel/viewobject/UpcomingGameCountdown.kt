package com.hongwei.android_nba_assist.viewmodel.viewobject

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
