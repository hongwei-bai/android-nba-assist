package com.mikeapp.sportsmate

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15000L
        const val HTTP_WRITE_TIMEOUT = 15000L
        const val HTTP_CONNECT_TIMEOUT = 15000L

        const val SPORTS_HUB_GITHUB_API_BASE = "https://api.github.com/"
        const val NBA_STAT_ENDPOINT = "/nba/"
        const val SOCCER_STAT_ENDPOINT = "/soccer/"

        const val nbaThemeEndpoint = "/nba/theme_v1/team_detail.json"
        const val nbaSeasonStatusEndpoint = "/nba/season_status.json"

        const val PLACEHOLDER_WIDTH = "{width}"
        const val NBA_APP_IMG_ENDPOINTS = "/nba"
        const val NBA_BANNER_PATH = "$NBA_APP_IMG_ENDPOINTS/banner/"
        const val NBA_LOGO_PATH = "$NBA_APP_IMG_ENDPOINTS/logo/"

        const val DEFAULT_BANNER_WIDTH = "1080"
        const val DEFAULT_LOGO_WIDTH = "320"
        const val DEFAULT_BANNER_EXTENSION = ".jpg"
        const val DEFAULT_LOGO_EXTENSION = ".png"

        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_TOKEN = "Bearer ${BuildConfig.STATIC_API_TOKEN}"

        object HttpCode {
            const val HTTP_OK = 200
            const val HTTP_DATA_UP_TO_DATE = 205
        }
    }

    object LogoConfiguration {
        const val useLocalLogos: Boolean = false
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