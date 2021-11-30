package com.hami.sports_assist.news

import androidx.lifecycle.*
import com.hami.sports_assist.ExceptionHelper
import com.hami.sports_assist.data.NbaStatRepository
import com.hami.sports_assist.data.local.AppSettings
import com.hami.sports_assist.data.room.nba.NbaTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = nbaStatRepository.dataStatus.asLiveData()

    var isOnlyShowTransactionOfMyTeam: Boolean = false

    val transactions: LiveData<List<NbaTransaction>> =
        nbaStatRepository.getTransactions()
            .map { transaction ->
                transaction.transactions.filter {
                    if (isOnlyShowTransactionOfMyTeam) {
                        it.teamAbbr.lowercase(Locale.US) == AppSettings.myNbaTeam
                    } else {
                        true
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + ExceptionHelper.nbaExceptionHandler) {
            nbaStatRepository.fetchTransactionsFromBackend()
            isRefreshing.postValue(false)
        }
    }
}