package com.mulholland.bookt.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.mulholland.bookt.ui.models.Event
import com.mulholland.bookt.ui.repositories.EventsRepository
import com.mulholland.bookt.ui.repositories.UserRepository

class EventsScreenState {
    val _events = mutableStateListOf<Event>()
    val events: List<Event> get() = _events
}

class EventsViewModel(application: Application): AndroidViewModel(application){
    val uiState = EventsScreenState()

    fun signOutUser() {
        UserRepository.signOutUser()
    }

    suspend fun getEvents() {
        val events = EventsRepository.getEvents()
        uiState._events.clear()
        uiState._events.addAll(events)
    }
}