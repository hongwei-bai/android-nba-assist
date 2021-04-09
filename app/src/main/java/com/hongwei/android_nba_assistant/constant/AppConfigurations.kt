package com.hongwei.android_nba_assistant.constant

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 5000L
        const val HTTP_WRITE_TIMEOUT = 5000L
        const val HTTP_CONNECT_TIMEOUT = 5000L

        const val HONGWEI_SERVICE_DOMAIN = "https://hongwei-test1.top"
//        const val NBA_STAT_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-home/"
        const val NBA_STAT_ENDPOINT = "http://10.0.2.2:8081/"

        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"
    }

    object Mock {
        const val TEAM_SCHEDULE_JSON = "mock/schedule-{team}.json"
    }

    object Date {
        const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

}