package com.hongwei.android_nba_assistant.constant

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 5000L
        const val HTTP_WRITE_TIMEOUT = 5000L
        const val HTTP_CONNECT_TIMEOUT = 5000L

        const val HONGWEI_SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val NBA_STAT_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-home/"
//        const val NBA_STAT_ENDPOINT = "http://10.0.2.2:8081/"
//        const val NBA_STAT_ENDPOINT = "http://10.0.2.2:8080/application-service-home/"

        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"
    }

    object TeamScheduleConfiguration {
        const val DAYS_PER_CALENDAR_ROW = 7
        const val DISPLAY_HOURS_IN_HOURS = 8
        const val DISPLAY_COUNTDOWN_IN_HOURS = 2
        const val COUNTDOWN_ZERO_FREEZE_MILLIS = 10L
        const val DISPLAY_STARTED_FROM_MINUTES = 5
        const val IGNORE_TODAY_S_GAME_FROM_HOURS = 1
    }

    object Mock {
        const val TEAM_SCHEDULE_JSON = "mock/schedule-{team}.json"
    }

    object Date {
        const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

    object Debug {
        const val DEBUG_CALENDAR = false
    }
}