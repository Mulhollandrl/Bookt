package com.mulholland.bookt.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.mulholland.bookt.ui.models.DailyAvailability
import com.mulholland.bookt.ui.repositories.EventsRepository
import com.mulholland.bookt.ui.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DailyAvailabilitiesScreenState {
    var dayAvailability by mutableStateOf(ArrayList<Int>(24))
    var allAvailabilities by mutableStateOf(ArrayList<DailyAvailability>())
    var id by mutableStateOf("")
}

class DailyAvailabilitiesViewModel(application: Application): AndroidViewModel(application) {
    val uiState = DailyAvailabilitiesScreenState()

    private var today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    private var daysNext: Long = 0

    fun initialize() {
        uiState.dayAvailability.clear()

        for (i in 1..24) {
            uiState.dayAvailability.add(0)
        }
    }

    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") {
            return
        } else {
            uiState.id = id
            val event = EventsRepository.getEvents().find {it.id == id}

            if (event == null) {

            } else {
                uiState.allAvailabilities = (event.availabilities as ArrayList<DailyAvailability>?)!!
            }
        }
    }

    fun saveCurrentAvailability() {
        val dailyAvailability = DailyAvailability(id = null, title = getCurrentDayString(), availabilities = uiState.dayAvailability, userId = UserRepository.getCurrentUserId())

        val savedDailyAvailability = uiState.allAvailabilities.indexOfFirst {
            it.title == getCurrentDayString() && it.userId == UserRepository.getCurrentUserId()
        }

        if (savedDailyAvailability < 0) {
            uiState.allAvailabilities.add(dailyAvailability)
        } else {
            uiState.allAvailabilities[savedDailyAvailability] = dailyAvailability
        }
    }

    suspend fun saveAllAvailabilities(scope: CoroutineScope) {
        val event = EventsRepository.getEvents().find { it.id == uiState.id } ?: return

        scope.async {
            EventsRepository.updateEvent(
                event.copy(
                    availabilities = uiState.allAvailabilities
                )
            )
        }.await()
    }

    fun getCurrentDayString(): String {
        return today
    }

    fun nextDay(): String {
        daysNext = 1
        today = LocalDate.parse(today.toString()).plusDays(daysNext).toString().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        return today
    }

    fun pastDay(): String {
        daysNext = -1
        today = LocalDate.parse(today.toString()).plusDays(daysNext).toString().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        return today
    }

    fun getCurrentDayAvailability(): DailyAvailability? {
        val dailyAvailability = uiState.allAvailabilities.find { it.title == getCurrentDayString() && it.userId == UserRepository.getCurrentUserId()}

        if (dailyAvailability == null) {
            initialize()
        } else {
            uiState.dayAvailability = dailyAvailability.availabilities as ArrayList<Int>
        }

        return DailyAvailability(
            id = "",
            userId = "",
            title = getCurrentDayString(),
            availabilities = uiState.dayAvailability
        )
    }
}