package com.mulholland.bookt.ui.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.mulholland.bookt.ui.models.DailyAvailability
import com.mulholland.bookt.ui.models.Event
import com.mulholland.bookt.ui.models.IdEmailPair
import kotlinx.coroutines.tasks.await

object InviteRepository {
    suspend fun getUsers(): List<IdEmailPair> {
        val snapshot = UserRepository.getCurrentUserId()?.let {
            Firebase.firestore
                .collection("users")
                .get()
                .await()
        }

        if (snapshot != null) {
            return snapshot.toObjects()
        }

        return ArrayList<IdEmailPair>()
    }

    suspend fun createUser(
        id: String,
        email: String
    ) {
        val doc = Firebase.firestore.collection("users").document()
        val idEmailPair = IdEmailPair(
            id = id,
            email = email
        )

        getUsers().find { it.id == id } ?: doc.set(idEmailPair).await()
    }

    suspend fun inviteUser(
        eventId: String,
        email: String
    ): Int {
        val event = EventsRepository.getEvents().find { it.id == eventId }
        val userIds: ArrayList<String> = event?.userIds as ArrayList<String>

        val user = getUsers().find { it.email == email } ?: return 0

        user.id?.let { userIds.add(it) }

        EventsRepository.updateEvent(
                event.copy(
                    userIds = userIds
                )
        )

        return 1
    }
}