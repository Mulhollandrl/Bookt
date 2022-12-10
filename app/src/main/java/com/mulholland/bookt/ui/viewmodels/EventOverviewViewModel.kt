package com.mulholland.bookt.ui.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.mulholland.bookt.ui.models.DailyAvailability
import com.mulholland.bookt.ui.models.DayCountPair
import com.mulholland.bookt.ui.models.HourCountPair
import com.mulholland.bookt.ui.repositories.EventsRepository
import com.mulholland.bookt.ui.repositories.UserRepository

class EventOverviewScreenState {
    var title by mutableStateOf("")
    var availabilities: List<DailyAvailability> by mutableStateOf(ArrayList<DailyAvailability>())
}

class EventOverviewViewModel(application: Application): AndroidViewModel(application){
    val uiState = EventOverviewScreenState()
    var id: String? = null

    suspend fun saveCurrentEvent() {
        if (id == null) {
            EventsRepository.createEvent(
                title = uiState.title,
                availabilities = uiState.availabilities
            )
        } else {
            val event = EventsRepository.getEvents().find { it.id == id } ?: return

            EventsRepository.updateEvent(
                event.copy(
                    title = uiState.title,
                    availabilities = uiState.availabilities
                )
            )
        }
    }

    suspend fun deleteCurrentEvent() {
        if (id == null) {
            return
        } else {
            val event = EventsRepository.getEvents().find { it.id == id } ?: return

            EventsRepository.deleteEvent(id!!)
        }
    }

    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") {
            return
        } else {
            this.id = id
            val event = EventsRepository.getEvents().find {it.id == id} ?: return

            uiState.title = event.title ?: ""
            uiState.availabilities = event.availabilities!!
        }
    }

    fun findBestTime(): String {
        val hourCounts = ArrayList<HourCountPair>()

        for (dayAvailability in uiState.availabilities) {
            if (dayAvailability.availabilities?.isEmpty() == true) {
                return "None"
            }
            for (hourIndex in 0 until dayAvailability.availabilities!!.size) {
                hourCounts.add(HourCountPair(hour = "$hourIndex:00", count = 0))

                if (dayAvailability.availabilities[hourIndex] == 0) {
                    hourCounts[hourIndex].count++
                } else if (dayAvailability.availabilities[hourIndex] == 1) {
                    hourCounts[hourIndex].count += 2
                } else {
                    hourCounts[hourIndex].count -= 1
                }
            }
        }

        hourCounts.sortBy { it.count }

        if (hourCounts.size <= 0) {
            return "None"
        }

        return hourCounts[0].hour
    }

    fun findBestDay(): String {
        val dayCounts = ArrayList<DayCountPair>()

        for (dayAvailability in uiState.availabilities) {
            var count = 0

            if (dayAvailability.availabilities?.isEmpty() == true) {
                return "None"
            }

            for (hourAvailability in dayAvailability.availabilities!!) {
                if (hourAvailability == 0) {
                    count++
                } else if (hourAvailability == 1) {
                    count += 2
                } else {
                    count -= 1
                }
            }

            dayAvailability.title?.let { DayCountPair(it, count) }?.let { dayCounts.add(it) }
        }

        dayCounts.sortBy { it.count }

        if (dayCounts.size <= 0) {
            return "None"
        }

        return dayCounts[0].day
    }
}