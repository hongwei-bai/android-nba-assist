package com.hami.sports_assist.season

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hami.sports_assist.data.league.nba.getConferenceByTeam
import com.hami.sports_assist.data.local.AppSettings
import com.hami.sports_assist.season.common.SeasonStatus
import com.hami.sports_assist.season.playin.PlayInTournament
import com.hami.sports_assist.season.playoff.PlayOff
import com.hami.sports_assist.ui.animation.ErrorView
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.ui.component.DataStatus
import com.hami.sports_assist.ui.component.DataStatusSnackBar
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Season() {
    val seasonViewModel = hiltViewModel<SeasonViewModel>()
    val seasonStatus = seasonViewModel.seasonStatus.observeAsState().value
    val dataStatus = seasonViewModel.dataStatus.observeAsState().value
    val coroutineScope = rememberCoroutineScope()
    if (seasonStatus != null) {
        val pages = when (seasonStatus) {
            SeasonStatus.PreSeason,
            SeasonStatus.RegularSeason -> listOf(SeasonScreens.WestStanding, SeasonScreens.EastStanding)
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
        val tabForActiveStage = SeasonScreens.fromSeasonStatus(seasonStatus, getConferenceByTeam(AppSettings.myNbaTeam))
        val tabPosition = pages.indexOf(tabForActiveStage)
        val pagerState = rememberPagerState(
            initialPage = tabPosition
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(seasonViewModel.isRefreshing.observeAsState().value == true),
            onRefresh = { seasonViewModel.refresh() },
        ) {
            Column {
                DataStatusSnackBar(dataStatus)
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(
                                pagerState, tabPositions
                            )
                        )
                    }
                ) {
                    pages.forEachIndexed { index, stage ->
                        Tab(
                            icon = {
                                Icon(
                                    imageVector = stage.icon,
                                    tint = if (tabPosition == index) MaterialTheme.colors.secondary
                                    else MaterialTheme.colors.onPrimary,
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

                HorizontalPager(count = pages.size, state = pagerState) { page ->
                    when (pages[page]) {
                        SeasonScreens.WestStanding -> Standing(seasonViewModel.westernStanding.observeAsState().value, true)
                        SeasonScreens.WestPlayIn -> PlayInTournament(null, true)
                        SeasonScreens.WestPlayOff -> PlayOff(null, true)
                        SeasonScreens.Final -> Final(null)
                        SeasonScreens.EastPlayOff -> PlayOff(null, false)
                        SeasonScreens.EastPlayIn -> PlayInTournament(null, false)
                        SeasonScreens.EastStanding -> Standing(seasonViewModel.easternStanding.observeAsState().value, false)
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
