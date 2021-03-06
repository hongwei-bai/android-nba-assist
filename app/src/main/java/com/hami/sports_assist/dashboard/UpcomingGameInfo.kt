package com.hami.sports_assist.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hami.sports_assist.R
import com.hami.sports_assist.ui.component.TeamLogo
import com.hami.sports_assist.util.LocalDateTimeUtil.getLocalDateDisplay
import com.hami.sports_assist.util.LocalDateTimeUtil.getLocalTimeDisplay

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
                    .background(MaterialTheme.colors.primary)
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
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = stringResource(R.string.game_vs_at),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = getLocalTimeDisplay(event.unixTimeStamp),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary
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