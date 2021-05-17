package com.hongwei.android_nba_assist.view.season

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hongwei.android_nba_assist.view.main.DataStatusSnackBar
import com.hongwei.android_nba_assist.view.season.playin.PlayInTournament
import com.hongwei.android_nba_assist.view.season.playoff.PlayOff
import com.hongwei.android_nba_assist.viewmodel.SeasonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Season() {
    val pages = listOf(
        SeasonScreens.WestStanding,
        SeasonScreens.WestPlayIn,
        SeasonScreens.WestPlayOff,
        SeasonScreens.Final,
        SeasonScreens.EastPlayOff,
        SeasonScreens.EastPlayIn,
        SeasonScreens.EastStanding
    )
    val pagerState = rememberPagerState(pageCount = pages.size)

    val seasonViewModel = hiltNavGraphViewModel<SeasonViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(seasonViewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { seasonViewModel.refresh() },
    ) {
        Column {
            DataStatusSnackBar(seasonViewModel.dataStatus.observeAsState().value)
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
                                contentDescription = null
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            GlobalScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> Standing(seasonViewModel.westernStanding.observeAsState().value, true)
                    1 -> PlayInTournament(
                        seasonViewModel.westernStanding.observeAsState().value,
                        seasonViewModel.westernPlayIn.observeAsState().value,
                        true
                    )
                    2 -> PlayOff(
                        seasonViewModel.westernStanding.observeAsState().value,
                        seasonViewModel.westernPlayOff.observeAsState().value,
                        true
                    )
                    3 -> Final(seasonViewModel.playOffGrandFinal.observeAsState().value)
                    4 -> PlayOff(
                        seasonViewModel.easternStanding.observeAsState().value,
                        seasonViewModel.westernPlayOff.observeAsState().value,
                        false
                    )
                    5 -> PlayInTournament(
                        seasonViewModel.easternStanding.observeAsState().value,
                        seasonViewModel.easternPlayIn.observeAsState().value,
                        false
                    )
                    6 -> Standing(seasonViewModel.easternStanding.observeAsState().value, false)
                    else -> Text(
                        text = "Page: $page",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}