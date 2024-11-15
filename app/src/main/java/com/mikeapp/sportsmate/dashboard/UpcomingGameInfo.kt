package com.mikeapp.sportsmate.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikeapp.sportsmate.R
import com.mikeapp.sportsmate.ui.component.TeamLogo
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getLocalDateDisplay
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getLocalTimeDisplay

@Composable
fun UpcomingGameInfo(myTeam: String?, event: EventDetailViewObject?) {
    if (myTeam != null && event != null) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(
                        bottomStart = 3.dp, bottomEnd = 3.dp,
                        topStart = 10.dp, topEnd = 10.dp
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 3.dp, bottomEnd = 3.dp,
                            topStart = 10.dp, topEnd = 10.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                TeamLogo(
                    logoUrl = event.guestTeam.logo,
                    modifier = Modifier.size(110.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getLocalDateDisplay(event.unixTimeStamp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = stringResource(R.string.game_vs_at),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = getLocalTimeDisplay(event.unixTimeStamp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                TeamLogo(
                    logoUrl = event.homeTeam.logo,
                    modifier = Modifier.size(110.dp)
                )
            }
        }
    }
}

data class EventDetailViewObject(
    val unixTimeStamp: Long,
    val guestTeam: TeamViewObject,
    val homeTeam: TeamViewObject
)

data class TeamViewObject(
    val abbrev: String,
    val name: String?,
    val logo: String
)