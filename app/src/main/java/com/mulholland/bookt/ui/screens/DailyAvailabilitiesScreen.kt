package com.mulholland.bookt.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mulholland.bookt.ui.components.DayAvailability
import com.mulholland.bookt.ui.components.Time
import com.mulholland.bookt.ui.viewmodels.DailyAvailabilitiesViewModel
import kotlinx.coroutines.launch

@Composable
fun DailyAvailabilitiesScreen(navHostController: NavHostController, id: String?) {
    val viewModel:DailyAvailabilitiesViewModel = viewModel()
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()

    var day by remember{ mutableStateOf(viewModel.getCurrentDayString()) }
    var dailyAvailability by remember{ mutableStateOf(viewModel.getCurrentDayAvailability()) }

    var currentTime = 0

    LaunchedEffect(true) {
        viewModel.setupInitialState(id)
    }

    Column(
//        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                day = viewModel.pastDay()
                dailyAvailability = viewModel.getCurrentDayAvailability()
                scope.launch {
                    viewModel.saveCurrentAvailability()
                }
            }) {
                Text(text = "Previous Day")
            }

            Button(onClick = {
                day = viewModel.nextDay()
                dailyAvailability = viewModel.getCurrentDayAvailability()
            }) {
                Text(text = "Next Day")
            }
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
                        val new = uiState.dayAvailability[currentTime]
                        Time(id = currentTime, time = "$currentTime:00", state = uiState.dayAvailability[currentTime], onClick = {uiState.dayAvailability[it.id] = it.state})
                    } else {
                        Time(id = currentTime, time = "$currentTime:00", state = 0, onClick = {uiState.dayAvailability[it.id] = it.state})
                    }
                    currentTime += 1
                }
            }
        }

        BackHandler {
            scope.launch {
                viewModel.saveAllAvailabilities(scope)
            }
            navHostController.navigateUp()
        }
    }
}