package com.hongwei.android_nba_assist.compat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.usecase.ForceRequestStandingUseCase
import com.hongwei.android_nba_assist.usecase.StandingUseCase
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.LoadingStatus
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.TeamStandingViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingViewModel @Inject constructor(
    private val forceRequestStandingUseCase: ForceRequestStandingUseCase,
    private val standingUseCase: StandingUseCase,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {
    val loadingStatus: MutableLiveData<LoadingStatus> by lazy {
        MutableLiveData<LoadingStatus>()
    }
    val standingViewObjects: MutableLiveData<List<TeamStandingViewObject>> by lazy {
        MutableLiveData<List<TeamStandingViewObject>>()
    }

    init {
        exceptionHelper.postHandler = {
            viewModelScope.launch(Dispatchers.Main) { loadingStatus.value = LoadingStatus.Error }
        }
    }

    fun load() {
        loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            forceRequestStandingUseCase.forceRequestStandingFromServer()
            loadCache()
        }
    }

    fun loadCache() {
        viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
            loadingStatus.value = LoadingStatus.Loading
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            val standing = standingUseCase.getStanding()
            launch(Dispatchers.Main) {
                loadingStatus.value = LoadingStatus.Inactive
                standingViewObjects.value = standing
            }
        }
    }
}