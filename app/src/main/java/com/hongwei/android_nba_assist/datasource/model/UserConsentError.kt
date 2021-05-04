package com.hongwei.android_nba_assist.datasource.model

sealed class UserConsentError

object NetworkError : UserConsentError()

object ApiError : UserConsentError()

object GenericError : UserConsentError()