package com.mikeapp.sportshub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikeapp.sportshub.domain.NbaHubUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val nbaHubUseCase: NbaHubUseCase) : ViewModel() {
    fun queryOnce() {
        viewModelScope.launch(IO) {
            nbaHubUseCase.queryOnce()
        }
    }
}