package com.hongwei.android_nba_assist.season.common

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
import com.hongwei.android_nba_assist.data.league.Tournament
import com.hongwei.android_nba_assist.util.ResourceByNameUtil
import com.hongwei.android_nba_assist.ui.component.TeamLogo
import com.hongwei.android_nba_assist.season.playin.PlayInHelper
import com.hongwei.android_nba_assist.season.playin.SeasonTeamStatus


@Composable
fun SeasonTeamLogoWrapper(teamAbbr: String, teamStatus: SeasonTeamStatus = SeasonTeamStatus.Normal) {
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
                    SeasonTeamStatus.Normal -> MaterialTheme.colors.onPrimary
                    SeasonTeamStatus.Eliminated -> PlayInHelper.EliminatedTeamTextColor
                    SeasonTeamStatus.NonParticipate -> PlayInHelper.NonParticipatorTextColor
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
                        SeasonTeamStatus.Normal -> 1f
                        SeasonTeamStatus.Eliminated -> PlayInHelper.EliminatedAlpha
                        SeasonTeamStatus.NonParticipate -> PlayInHelper.NonParticipatorAlpha
                    }
                )
        )
    }
}