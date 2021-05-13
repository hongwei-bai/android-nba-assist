package com.hongwei.android_nba_assist.view.season

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun Season() {
    val pagerState = rememberPagerState(pageCount = 3)
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> Standing()
            else -> Text(
                text = "Page: $page",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}