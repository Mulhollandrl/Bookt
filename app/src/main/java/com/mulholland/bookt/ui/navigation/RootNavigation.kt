package com.mulholland.bookt.ui.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.mulholland.bookt.ui.screens.*

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route,
    ) {
        composable(Routes.Splash.route) { SplashScreen(navHostController = navController) }
        composable(Routes.SignIn.route) { SignInScreen(navHostController = navController) }
        composable(Routes.Events.route) { EventsScreen(navHostController = navController) }
        composable(Routes.EventOverview.route) { EventOverviewScreen(navHostController = navController, id = null) }
        composable(Routes.Daily.route) { DailyAvailabilitiesScreen(navHostController = navController, id = null) }
        composable(
            route = "daily?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = "new" })
        ) {
                navBackStackEntry ->
            DailyAvailabilitiesScreen(navHostController = navController, navBackStackEntry.arguments?.get("id").toString())
        }
        composable(
            route = "editevent?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = "new" })
        ) {
                navBackStackEntry ->
            EventOverviewScreen(navHostController = navController, navBackStackEntry.arguments?.get("id").toString())
        }
        composable(
            route = "invite?id={id}",
            arguments = listOf(navArgument("id") { defaultValue = "new" })
        ) {
                navBackStackEntry ->
            InviteScreen(navHostController = navController, navBackStackEntry.arguments?.get("id").toString())
        }
    }
}