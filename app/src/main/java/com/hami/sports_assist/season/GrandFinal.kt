package com.hami.sports_assist.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hami.sports_assist.R
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.ui.theme.BlackAlphaA0

@Composable
fun Final(playOffGrandFinalViewObject: PlayOffGrandFinalViewObject?) {
    playOffGrandFinalViewObject?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxSize()
                    .background(color = BlackAlphaA0)
                    .verticalScroll(rememberScrollState())
            ) {
                GrandFinalHeader()
                Spacer(modifier = Modifier.size(48.dp))
                Text(
                    text = "TBD",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(end = 48.dp)
                )
                Spacer(modifier = Modifier.size(18.dp))
                Text(
                    text = "vs",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Spacer(modifier = Modifier.size(18.dp))
                Text(
                    text = "TBD",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 48.dp)
                )
            }
        }
    } ?: LoadingContent(modifier = Modifier.background(color = MaterialTheme.colors.primary))
}

@Composable
private fun GrandFinalHeader() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(R.string.season_grand_final),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

data class PlayOffGrandFinalViewObject(
    val teamFromWestern: String,
    val teamFromEastern: String,
    val scoreWesternWinner: Int,
    val scoreEasternWinner: Int,
    val winner: String
)