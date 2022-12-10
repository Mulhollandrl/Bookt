package com.mulholland.bookt.ui.models

data class Event(
    val id: String? = null,
    val userIds: List<String?>? = null,
    val title: String? = null,
    val availabilities: List<DailyAvailability>? = null
)
