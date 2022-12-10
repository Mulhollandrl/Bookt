package com.mulholland.bookt.ui

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth
import com.mulholland.bookt.ui.navigation.RootNavigation
import com.mulholland.bookt.ui.theme.BooktTheme

@Composable
fun App() {
    BooktTheme {
        RootNavigation()
    }
}