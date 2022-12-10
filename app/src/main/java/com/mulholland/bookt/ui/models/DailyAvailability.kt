package com.mulholland.bookt.ui.models

data class DailyAvailability(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val availabilities: List<Int>? = null
)
