package com.mikeapp.sportshub.di

import com.mikeapp.sportshub.data.GithubOpenApiRepository
import com.mikeapp.sportshub.data.SportsHubRepository
import com.mikeapp.sportshub.data.epsn.CurlEspnService
import com.mikeapp.sportshub.data.epsn.EspnParser
import com.mikeapp.sportshub.data.epsn.TimeStampUtil
import com.mikeapp.sportshub.domain.NbaHubUseCase
import com.mikeapp.sportshub.ui.HomeScreenViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
    single { TimeStampUtil() }

    single { CurlEspnService(get()) }
    single { EspnParser() }

    single { GithubOpenApiRepository() }
    single { SportsHubRepository(get(), get()) }

    single { NbaHubUseCase(get(), get()) }

    viewModel { HomeScreenViewModel(get()) }
}