package com.sena.crud.data.remote.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val auth: FirebaseAuth? get() = FirebaseApp.getApps(context).firstOrNull()?.let { FirebaseAuth.getInstance(it) }

    fun isConfigured(): Boolean = auth != null
    fun currentUserId(): String? = auth?.currentUser?.uid
    fun currentUserEmail(): String? = auth?.currentUser?.email
    fun logout() { auth?.signOut() }

    suspend fun register(email: String, password: String) {
        val instance = auth ?: error("Firebase no está configurado. Agrega google-services.json.")
        instance.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun login(email: String, password: String) {
        val instance = auth ?: error("Firebase no está configurado. Agrega google-services.json.")
        instance.signInWithEmailAndPassword(email, password).await()
    }
}
