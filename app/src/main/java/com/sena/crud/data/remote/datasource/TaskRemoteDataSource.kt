package com.sena.crud.data.remote.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.mapper.toMap
import com.sena.crud.data.remote.dto.TaskDto
import com.sena.crud.domain.model.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val firestore: FirebaseFirestore? = FirebaseApp.getApps(context).firstOrNull()?.let { FirebaseFirestore.getInstance(it) }
    private val collection get() = firestore?.collection("tasks")

    fun observeTasks(ownerId: String): Flow<List<Task>> = callbackFlow {
        val ref = collection
        if (ref == null) {
            close(IllegalStateException("Firebase no está configurado. Agrega google-services.json."))
            return@callbackFlow
        }
        val registration = ref.whereEqualTo("ownerId", ownerId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val tasks = snapshot?.documents.orEmpty().mapNotNull { doc ->
                runCatching {
                    TaskDto(doc.id, doc.getString("ownerId").orEmpty(), doc.getString("title").orEmpty(), doc.getString("description").orEmpty(), doc.getBoolean("completed") ?: false, doc.getLong("createdAt") ?: 0L, doc.getLong("updatedAt") ?: 0L).toDomain()
                }.getOrNull()
            }.sortedByDescending { it.createdAt }
            trySend(tasks)
        }
        awaitClose { registration.remove() }
    }

    private fun requireCollection() = collection ?: error("Firebase no está configurado. Agrega google-services.json.")

    suspend fun createTask(task: Task): Task {
        val ref = requireCollection().document()
        val saved = task.copy(id = ref.id)
        ref.set(saved.toMap()).await()
        return saved
    }
    suspend fun updateTask(task: Task): Task { requireCollection().document(task.id).update(task.toMap()).await(); return task }
    suspend fun deleteTask(task: Task) { requireCollection().document(task.id).delete().await() }
}
