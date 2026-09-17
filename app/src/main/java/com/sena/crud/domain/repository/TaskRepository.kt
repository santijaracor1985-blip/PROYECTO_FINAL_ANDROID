package com.sena.crud.domain.repository

import com.sena.crud.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(ownerId: String): Flow<List<Task>>
    suspend fun createTask(task: Task): Result<Task>
    suspend fun updateTask(task: Task): Result<Task>
    suspend fun deleteTask(task: Task): Result<Unit>
}
