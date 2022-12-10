package com.mulholland.bookt.ui.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object UserRepository {
    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUserId() != null
    }

    fun signOutUser(){
        Firebase.auth.signOut()
    }
}