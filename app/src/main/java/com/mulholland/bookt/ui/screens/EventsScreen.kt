package com.mulholland.bookt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mulholland.bookt.ui.components.EventListItem
import com.mulholland.bookt.ui.navigation.Routes
import com.mulholland.bookt.ui.theme.BooktTheme
import com.mulholland.bookt.ui.viewmodels.EventsViewModel
import org.intellij.lang.annotations.JdkConstants

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EventsScreen(navHostController: NavHostController) {
    val viewModel: EventsViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getEvents()
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.primary),
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(Routes.EventOverview.route) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
        }},
        content = {
            Column(modifier = Modifier.background(MaterialTheme.colors.secondary)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primaryVariant)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(onClick = {
                        viewModel.signOutUser()
                        navHostController.navigate(Routes.Splash.route)
                    }) {
                        Text(
                            text = "Sign Out"
                        )
                    }
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                        .background(MaterialTheme.colors.secondary)
                ) {
                    items(state.events, key = { it.id!! }) {
                        EventListItem(
                            event = it,
                            onEditPressed = {navHostController.navigate("editevent?id=${it.id}")}
                        )
                    }
                }
            }
        }
    )
}