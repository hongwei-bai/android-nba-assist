package com.mikeapp.sportsmate.season

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mikeapp.sportsmate.data.league.nba.getConferenceByTeam
import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.season.common.SeasonStatus
import com.mikeapp.sportsmate.season.playin.PlayInTournament
import com.mikeapp.sportsmate.season.playoff.PlayOff
import com.mikeapp.sportsmate.ui.animation.ErrorView
import com.mikeapp.sportsmate.ui.animation.LoadingContent
import com.mikeapp.sportsmate.ui.component.DataStatus
import com.mikeapp.sportsmate.ui.component.DataStatusSnackBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun Season() {
    val seasonViewModel = hiltViewModel<SeasonViewModel>()
    val seasonStatus = seasonViewModel.seasonStatus.observeAsState().value
    val dataStatus = seasonViewModel.dataStatus.observeAsState().value
    val coroutineScope = rememberCoroutineScope()
    if (seasonStatus != null) {
        val pages = when (seasonStatus) {
            SeasonStatus.PreSeason,
            SeasonStatus.RegularSeason -> listOf(
                SeasonScreens.WestStanding,
                SeasonScreens.EastStanding
            )

            else -> listOf(
                SeasonScreens.WestStanding,
                SeasonScreens.WestPlayIn,
                SeasonScreens.WestPlayOff,
                SeasonScreens.Final,
                SeasonScreens.EastPlayOff,
                SeasonScreens.EastPlayIn,
                SeasonScreens.EastStanding
            )
        }
        val tabForActiveStage =
            SeasonScreens.fromSeasonStatus(seasonStatus, getConferenceByTeam(AppSettings.myNbaTeam))
        val tabPosition = pages.indexOf(tabForActiveStage)
        val pagerState = rememberPagerState(
            pageCount = { pages.size }
        )

        PullToRefreshBox(
            isRefreshing = seasonViewModel.isRefreshing.observeAsState().value == true,
            onRefresh = { seasonViewModel.refresh() },
        ) {
            Column {
                DataStatusSnackBar(dataStatus)
                //TODO
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                .height(4.dp)
                                .fillMaxWidth()
                                .background(Color.Blue)
                        )
                    }
                ) {
                    pages.forEachIndexed { index, stage ->
                        Tab(
                            icon = {
                                Icon(
                                    imageVector = stage.icon,
                                    tint = if (tabPosition == index) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = null
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            },
                        )
                    }
                }

                HorizontalPager(state = pagerState) { page ->
                    when (pages[page]) {
                        SeasonScreens.WestStanding -> Standing(
                            seasonViewModel.westernStanding.observeAsState().value,
                            true
                        )

                        SeasonScreens.WestPlayIn -> PlayInTournament(null, true)
                        SeasonScreens.WestPlayOff -> PlayOff(null, true)
                        SeasonScreens.Final -> Final(null)
                        SeasonScreens.EastPlayOff -> PlayOff(null, false)
                        SeasonScreens.EastPlayIn -> PlayInTournament(null, false)
                        SeasonScreens.EastStanding -> Standing(
                            seasonViewModel.easternStanding.observeAsState().value,
                            false
                        )
                    }
                }
            }
        }
    } else {
        when (dataStatus) {
            is DataStatus.ServiceError -> ErrorView(dataStatus.message)
            else -> LoadingContent()
        }
    }
}
