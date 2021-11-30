package com.hami.sports_assist.data

sealed class UserConsentError

object NetworkError : UserConsentError()

object ApiError : UserConsentError()

object GenericError : UserConsentError()