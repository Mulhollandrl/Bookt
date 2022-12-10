package com.mulholland.bookt.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.mulholland.bookt.R
import com.mulholland.bookt.ui.repositories.InviteRepository
import com.mulholland.bookt.ui.repositories.UserRepository

class SignInViewModel(application: Application): AndroidViewModel(application){
    fun isUserSignedIn(): Boolean {
        return UserRepository.isUserLoggedIn()
    }

    suspend fun saveUser() {
        val id = UserRepository.getCurrentUserId()
        val email = FirebaseAuth.getInstance().currentUser?.email

        if (id != null) {
            if (email != null) {
                InviteRepository.createUser(id = id, email = email)
            } else {
                InviteRepository.createUser(id = id, email = "")
            }
        }
    }
}