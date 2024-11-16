package com.mikeapp.sportsmate.dashboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.mikeapp.sportsmate.R

class UrlProvider : PreviewParameterProvider<Int?> {
    override val values = sequenceOf(0, 1, 82, null)
    override val count: Int = values.count()
}

@Preview
@Composable
fun GamesLeftView(@PreviewParameter(UrlProvider::class) gamesLeft: Int?) {
    Row(verticalAlignment = Alignment.Bottom) {
        when (gamesLeft) {
            null -> {
                Text(
                    text = " ",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            0 -> Text(
                text = stringResource(R.string.no_games_left),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 24.dp)
            )
            else -> {
                Text(
                    text = gamesLeft.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = stringResource(R.string.games_left),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}