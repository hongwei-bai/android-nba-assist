package com.mikeapp.sportsmate.data

sealed class UserConsentError

object NetworkError : UserConsentError()

object ApiError : UserConsentError()

object GenericError : UserConsentError()