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
import com.hongwei.android_nba_assist.viewmodel.StandingViewModel
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

    val standingViewModel = hiltNavGraphViewModel<StandingViewModel>()

    Column {
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
                0 -> Standing(standingViewModel.westernStanding.observeAsState().value, true)
                6 -> Standing(standingViewModel.easternStanding.observeAsState().value, false)
                else -> Text(
                    text = "Page: $page",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}