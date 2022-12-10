package com.mulholland.bookt.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.mulholland.bookt.ui.repositories.EventsRepository
import com.mulholland.bookt.ui.repositories.InviteRepository

class InviteScreenState {
    var email by mutableStateOf("")
    var id by mutableStateOf("")
}

class InviteViewModel(application: Application): AndroidViewModel(application){
    val uiState = InviteScreenState()

    fun setupInitialState(id: String?) {
        if (id == null || id == "new") {
            return
        } else {
            uiState.id = id
        }
    }

    suspend fun inviteUser() {
        InviteRepository.inviteUser(eventId = uiState.id, email = uiState.email)
    }
}