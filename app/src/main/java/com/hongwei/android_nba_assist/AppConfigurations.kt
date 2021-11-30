package com.hongwei.android_nba_assist

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15000L
        const val HTTP_WRITE_TIMEOUT = 15000L
        const val HTTP_CONNECT_TIMEOUT = 15000L

        const val HONGWEI_SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val NBA_STAT_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-sports/nba/"
        const val SOCCER_STAT_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-sports/soccer/"
//        const val NBA_STAT_ENDPOINT = "http://10.0.2.2:8081/nba/"
//        const val NBA_STAT_ENDPOINT = "http://10.0.2.2:8080/application-service-sports/nba/"

        const val NBA_THEME_ENDPOINT = "$HONGWEI_SERVICE_DOMAIN/application-service-sports/nba/"

        const val PLACEHOLDER_WIDTH = "{width}"
        const val NBA_APP_IMG_ENDPOINTS = "$HONGWEI_SERVICE_DOMAIN/resize/$PLACEHOLDER_WIDTH/nba_v1"
        const val NBA_BANNER_PATH = "$NBA_APP_IMG_ENDPOINTS/banner/"
        const val NBA_LOGO_PATH = "$NBA_APP_IMG_ENDPOINTS/logo/"

        const val DEFAULT_BANNER_WIDTH = "1080"
        const val DEFAULT_LOGO_WIDTH = "320"
        const val DEFAULT_BANNER_EXTENSION = ".jpg"
        const val DEFAULT_LOGO_EXTENSION = ".png"

        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"

        object HttpCode {
            const val HTTP_OK = 200
            const val HTTP_DATA_UP_TO_DATE = 205
        }
    }

    object LogoConfiguration {
        const val useLocalLogos: Boolean = true
    }

    object TeamScheduleConfiguration {
        const val DAYS_PER_CALENDAR_ROW = 7
        const val DISPLAY_HOURS_IN_HOURS = 8
        const val DISPLAY_COUNTDOWN_IN_HOURS = 2
        const val COUNTDOWN_ZERO_FREEZE_MILLIS = 30L
        const val DISPLAY_STARTED_FROM_MINUTES = 5
        const val IGNORE_TODAY_S_GAME_FROM_HOURS = 1
        const val MARK_TODAY_EVENT_DIM_AFTER_HOURS = 2
    }

    object ForceRefreshInterval {
        const val FOR_SCHEDULE_HOUR = 6
        const val FOR_STANDING_HOUR = 6
        const val FOR_STANDING_PLAYOFF = 6
    }

    object Room {
        const val API_VERSION = 1
    }

    object Mock {
        const val TEAM_SCHEDULE_JSON = "mock/schedule_{team}.json"
    }

    object Date {
        const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    }

    object Debug {
        const val DEBUG_TEAM_THEME_ON_LOCAL = false

        const val DEBUG_CALENDAR = false
    }

    object View {
        const val WIN_SYMBOL = "W"
        const val LOSE_SYMBOL = "L"
    }
}