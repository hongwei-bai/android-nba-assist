package com.hongwei.android_nba_assistant.constant

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 5000L
        const val HTTP_WRITE_TIMEOUT = 5000L
        const val HTTP_CONNECT_TIMEOUT = 5000L

        const val HONGWEI_SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val NBA_STAT_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-nba/"
    }

    object Stub {
        const val GS_SCHEDULE_JSON = "schedule/gs/schedule.json"
    }

    object Date {
        const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

}