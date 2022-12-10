package com.mulholland.bookt.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mulholland.bookt.ui.models.IdStatePair

fun findColor(state: Int): Color {
    if (state == 0) {
        return Color.Gray
    } else if (state == 1) {
        return Color.Green
    } else {
        return Color.Red
    }
}

@Composable
fun Time(
    id: Int,
    time: String,
    state: Int,
    onClick: (IdStatePair) -> Unit
) {
    var currentAvailability = remember {mutableStateOf(state)}
    var color = remember {mutableStateOf(findColor(state))}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (currentAvailability.value == 0) {
                    currentAvailability.value = 1
                    color.value = Color.Green
                    onClick(IdStatePair(id, currentAvailability.value))
                } else if (currentAvailability.value == 1) {
                    currentAvailability.value = 2
                    color.value = Color.Red
                    onClick(IdStatePair(id, currentAvailability.value))
                } else {
                    currentAvailability.value = 0
                    color.value = Color.Gray
                    onClick(IdStatePair(id, currentAvailability.value))
                }
            }
            .background(color = color.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}