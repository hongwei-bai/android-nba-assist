package com.mikeapp.sportsmate.season.playoff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikeapp.sportsmate.data.league.Tournament
import com.mikeapp.sportsmate.util.ResourceByNameUtil
import com.mikeapp.sportsmate.ui.component.TeamLogo
import com.mikeapp.sportsmate.ui.theme.Grey80

object PlayOffHelper {
    @Composable
    fun PlayOffTeamLogoWrapper(teamAbbr: String, playOffTeamStatus: PlayOffTeamStatus = PlayOffTeamStatus.Normal) {
        if (teamAbbr.equals(Tournament.TBD, true)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .size(40.dp)
            ) {
                Text(
                    text = Tournament.TBD,
                    style = MaterialTheme.typography.bodyLarge,
                    color = when (playOffTeamStatus) {
                        PlayOffTeamStatus.Normal -> MaterialTheme.colorScheme.onPrimary
                        PlayOffTeamStatus.Eliminated -> EliminatedTeamTextColor
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
                        when (playOffTeamStatus) {
                            PlayOffTeamStatus.Normal -> 1f
                            PlayOffTeamStatus.Eliminated -> EliminatedAlpha
                        }
                    )
            )
        }
    }

    private const val EliminatedAlpha = 0.4f
    private val EliminatedTeamTextColor = Grey80
}