package com.mulholland.bookt.ui.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.mulholland.bookt.ui.models.DailyAvailability
import com.mulholland.bookt.ui.models.Event
import kotlinx.coroutines.tasks.await

object EventsRepository {
    private val eventsCache = mutableListOf<Event>()
    private var cacheInitialized = false

    suspend fun getEvents(): List<Event> {
        if (!cacheInitialized) {
            cacheInitialized = true

            val snapshot = UserRepository.getCurrentUserId()?.let {
                Firebase.firestore
                    .collection("events")
                    .whereArrayContains("userIds", it)
                    .get()
                    .await()
            }

            if (snapshot != null) {
                eventsCache.addAll(snapshot.toObjects())
            }

            eventsCache.addAll(ArrayList<Event>(0))
        }

        return eventsCache
    }

    suspend fun createEvent(
        title: String,
        availabilities: List<DailyAvailability>
    ): Event {
        val doc = Firebase.firestore.collection("events").document()
        val event = Event(
            title = title,
            availabilities = availabilities,
            id = doc.id,
            userIds = mutableListOf(UserRepository.getCurrentUserId())
        )

        eventsCache.add(event)
        doc.set(event).await()
        return event
    }

    suspend fun updateEvent(event: Event) {
        val oldEventIndex = eventsCache.indexOfFirst {
            it.id == event.id
        }
        eventsCache[oldEventIndex] = event

        Firebase.firestore
            .collection("events")
            .document(event.id!!)
            .set(event)
            .await()
    }

    suspend fun deleteEvent(id: String) {
        val oldEventIndex = eventsCache.indexOfFirst {
            it.id == id
        }

        eventsCache.removeAt(oldEventIndex)
        Firebase.firestore
            .collection("events")
            .document(id)
            .delete()
            .await()
    }
}