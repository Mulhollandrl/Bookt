package com.mulholland.bookt.ui.navigation

data class Screen(val route: String)

object Routes {
    val Splash = Screen("splash")
    val SignIn = Screen("signin")
    val Events = Screen("events")
    val EventOverview = Screen("eventoverview")
    val Daily = Screen("daily")
}