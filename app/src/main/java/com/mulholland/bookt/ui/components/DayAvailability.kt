package com.mulholland.bookt.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mulholland.bookt.ui.models.DailyAvailability

@Composable
fun DayAvailability(
    day: String,
    dailyAvailability: DailyAvailability?
) {
    var currentTime = 0
    var dayAvailability = ArrayList<Int>(24)

    for (i in 1..24) {
        dayAvailability.add(0)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 1..24) {
                if (dailyAvailability != null) {
                    Time(id = currentTime, time = "$currentTime:00", state = dailyAvailability.availabilities?.get(currentTime) ?: 0, onClick = {dayAvailability[it.id] = it.state})
                } else {
                    Time(id = currentTime, time = "$currentTime:00", state = 0, onClick = {dayAvailability[it.id] = it.state})
                }
                currentTime += 1
            }
        }
    }
}

@Preview
@Composable
fun DayAvailabilityPreview() {
    DayAvailability(day = "1", dailyAvailability = null)
}