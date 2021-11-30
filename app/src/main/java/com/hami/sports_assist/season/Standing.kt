package com.hami.sports_assist.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hami.sports_assist.R
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.ui.component.TeamLogo
import com.hami.sports_assist.ui.theme.BlackAlphaA0
import com.hami.sports_assist.ui.theme.Grey50
import com.hami.sports_assist.ui.theme.Grey60
import com.hami.sports_assist.ui.theme.Red900
import com.hami.sports_assist.util.ResourceByNameUtil.getResourceIdByName

@Composable
fun Standing(standing: List<TeamStat>?, isLTR: Boolean) {
    standing?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            val modifier = if (isLTR) {
                Modifier
                    .padding(top = 6.dp, start = 6.dp, bottom = 6.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
            } else {
                Modifier
                    .padding(top = 6.dp, end = 6.dp, bottom = 6.dp)
                    .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = BlackAlphaA0)
                    .verticalScroll(rememberScrollState())
            ) {
                StandingHeader()
                standing.forEach {
                    Team(it)
                    if (it.rank == 10) {
                        Divider(color = Red900, thickness = 1.dp)
                    }
                }
            }
        }
    } ?: LoadingContent(modifier = Modifier.background(color = MaterialTheme.colors.primary))
}

@Composable
private fun StandingHeader() {
    Row {
        Spacer(Modifier.weight(5f))
        Column(Modifier.weight(1.2f)) {
            Text(
                text = stringResource(id = R.string.standing_header_win_lose),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.standing_header_games_back),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.standing_header_streak),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun Team(teamStat: TeamStat) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(45.dp)
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        val color = when (teamStat.rank) {
            in 1..6 -> MaterialTheme.colors.secondary
            in 7..8 -> MaterialTheme.colors.onPrimary
            in 9..10 -> Grey50
            else -> Grey60
        }
        val iconAlpha = when (teamStat.rank) {
            in 1..6 -> 1f
            in 7..8 -> 0.9f
            in 9..10 -> 0.7f
            else -> 0.5f
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(5f)
        ) {
            Text(
                text = teamStat.rank.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.End,
                modifier = Modifier.width(32.dp)
            )
            TeamLogo(
                logoUrl = teamStat.logoUrl,
                localPlaceholderResId = getResourceIdByName(LocalContext.current, teamStat.logoResourceName),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .size(40.dp)
                    .alpha(iconAlpha)
            )
            Text(
                text = teamStat.team,
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                softWrap = false
            )
        }
        Column(Modifier.weight(1.2f)) {
            Text(
                text = stringResource(id = R.string.standing_win_lose_template, teamStat.wins, teamStat.losses),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = teamStat.gamesBack.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(
                    id = R.string.standing_streak_template,
                    teamStat.currentStreak.first, teamStat.currentStreak.second
                ),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class TeamStat(
    val rank: Int,
    val teamAbbr: String,
    var team: String,
    val logoResourceName: String? = null,
    val logoUrl: String? = null,
    val wins: Int,
    val losses: Int,
    val gamesBack: Double,
    val currentStreak: Pair<String, Int>,
    val last10Records: Pair<Int, Int>
)