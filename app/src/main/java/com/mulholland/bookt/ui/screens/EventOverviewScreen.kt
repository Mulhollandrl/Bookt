package com.mulholland.bookt.ui.screens

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mulholland.bookt.R
import com.mulholland.bookt.ui.components.FormField
import com.mulholland.bookt.ui.navigation.Routes
import com.mulholland.bookt.ui.repositories.EventsRepository
import com.mulholland.bookt.ui.viewmodels.EventOverviewScreenState
import com.mulholland.bookt.ui.viewmodels.EventOverviewViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventOverviewScreen(navHostController: NavHostController, id: String?) {
    val viewModel: EventOverviewViewModel = viewModel()
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.setupInitialState(id)
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormField(
            value = uiState.title,
            onValueChange = {uiState.title = it},
            placeholder = { Text(text = "Title")}
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Most Available Time:")
                Text(text = viewModel.findBestTime())
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Most Available Day:")
                Text(text = viewModel.findBestDay())
            }
        }
        if (viewModel.id != null && viewModel.id != "new") {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navHostController.navigate(Routes.Daily.route) }
            ) {
                Text(text = "Edit Your Availabilities")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navHostController.navigate("invite?id=${id}") }
            ) {
                Text(text = "Invite Others")
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    viewModel.deleteCurrentEvent()
                }
                navHostController.navigate(Routes.Events.route)
            }
        ) {
            Text(text = "Delete")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    viewModel.saveCurrentEvent()
                }
                navHostController.navigate(Routes.Events.route)
            }
        ) {
            Text(text = "Save")
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}