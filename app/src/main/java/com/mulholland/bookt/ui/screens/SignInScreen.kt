package com.mulholland.bookt.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mulholland.bookt.R
import com.mulholland.bookt.ui.components.Loader
import com.mulholland.bookt.ui.navigation.Routes
import com.mulholland.bookt.ui.repositories.InviteRepository
import com.mulholland.bookt.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()

    LaunchedEffect(true) {
        val loginStatusCheck =
            withContext(Dispatchers.Default) {
                viewModel.isUserSignedIn()
            }

        delay(1000)

        viewModel.saveUser()

        if (loginStatusCheck) {
            navHostController.navigate(Routes.Events.route) {
                popUpTo(0)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Signing You In",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Loader()
    }
}