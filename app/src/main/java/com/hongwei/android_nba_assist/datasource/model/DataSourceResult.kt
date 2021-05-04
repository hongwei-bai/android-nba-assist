package com.hongwei.android_nba_assist.datasource.model

sealed class DataSourceResult<T>

class DataSourceSuccessResult<T>(val data: T) : DataSourceResult<T>()

class DataSourceErrorResult<T>(val error: UserConsentError) : DataSourceResult<T>()