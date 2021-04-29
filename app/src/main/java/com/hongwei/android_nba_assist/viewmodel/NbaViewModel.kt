package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NbaViewModel @Inject constructor() : ViewModel() {
    val team: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun start() {
        team.value = "gs"
    }

    fun toggle() {
        val previous = team.value
        team.value = when (previous) {
            "gs" -> "lal"
            else -> "gs"
        }
    }
}