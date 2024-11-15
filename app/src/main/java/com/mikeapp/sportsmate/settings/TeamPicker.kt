package com.mikeapp.sportsmate.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mikeapp.sportsmate.data.league.nba.easternTeams
import com.mikeapp.sportsmate.data.league.nba.westernTeams
import com.mikeapp.sportsmate.util.ResourceByNameUtil
import com.mikeapp.sportsmate.ui.component.TeamLogo

@ExperimentalFoundationApi
@Composable
fun TeamPickerDialog(onTeamPicked: (String?) -> Unit) {
    val allNbaTeams = westernTeams + easternTeams
    Dialog(onDismissRequest = { onTeamPicked.invoke(null) }) {
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                items((allNbaTeams).size) {
                    TeamPickerItem(
                        teamAbbr = allNbaTeams[it],
                        modifier = Modifier.clickable {
                            onTeamPicked.invoke(allNbaTeams[it])
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TeamPickerItem(teamAbbr: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(4.dp)
    ) {
        TeamLogo(
            localPlaceholderResId = ResourceByNameUtil.getResourceIdByName(LocalContext.current, teamAbbr),
            modifier = Modifier.padding(8.dp)
        )
    }
}