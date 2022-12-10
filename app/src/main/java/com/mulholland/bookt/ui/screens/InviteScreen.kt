package com.mulholland.bookt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mulholland.bookt.ui.components.FormField
import com.mulholland.bookt.ui.viewmodels.InviteViewModel
import kotlinx.coroutines.launch

@Composable
fun InviteScreen(navHostController: NavHostController, id: String?) {
    val viewModel: InviteViewModel = viewModel()
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()

    viewModel.setupInitialState(id = id)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Invite To Event",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        FormField(
            value = uiState.email,
            onValueChange = {uiState.email = it},
            placeholder = { Text(text = "Email Address") }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    viewModel.inviteUser()
                    navHostController.navigateUp()
                }
            }
        ) {
            Text(text = "Invite")
        }
    }
}