package com.hongwei.android_nba_assist.view.season.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hongwei.android_nba_assist.datasource.league.Tournament
import com.hongwei.android_nba_assist.util.ResourceByNameUtil
import com.hongwei.android_nba_assist.view.component.TeamLogo
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper
import com.hongwei.android_nba_assist.view.season.playin.PlayInTeamStatus


@Composable
fun SeasonTeamLogoWrapper(teamAbbr: String, teamStatus: PlayInTeamStatus = PlayInTeamStatus.Normal) {
    if (teamAbbr.equals(Tournament.TBD, true)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(40.dp)
        ) {
            Text(
                text = Tournament.TBD,
                style = MaterialTheme.typography.body1,
                color = when (teamStatus) {
                    PlayInTeamStatus.Normal -> MaterialTheme.colors.onPrimary
                    PlayInTeamStatus.Eliminated -> PlayInHelper.EliminatedTeamTextColor
                    PlayInTeamStatus.NonParticipate -> PlayInHelper.NonParticipatorTextColor
                },
                textAlign = TextAlign.Center
            )
        }
    } else {
        TeamLogo(
            localPlaceholderResId = ResourceByNameUtil.getResourceIdByName(LocalContext.current, teamAbbr),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(40.dp)
                .alpha(
                    when (teamStatus) {
                        PlayInTeamStatus.Normal -> 1f
                        PlayInTeamStatus.Eliminated -> PlayInHelper.EliminatedAlpha
                        PlayInTeamStatus.NonParticipate -> PlayInHelper.NonParticipatorAlpha
                    }
                )
        )
    }
}