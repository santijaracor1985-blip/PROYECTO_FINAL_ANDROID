package com.sena.crud.data.repository

import com.sena.crud.data.remote.datasource.TaskRemoteDataSource
import com.sena.crud.domain.model.Task
import com.sena.crud.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
 private val dataSource: TaskRemoteDataSource
) : TaskRepository {

 override fun observeTasks(
  ownerId: String
 ): Flow<List<Task>> {
  return dataSource.observeTasks(ownerId)
 }

 override suspend fun createTask(
  task: Task
 ): Result<Task> {
  return runCatching {
   dataSource.createTask(task)
  }
 }

 override suspend fun updateTask(
  task: Task
 ): Result<Task> {
  return runCatching {
   dataSource.updateTask(task)
  }
 }

 override suspend fun deleteTask(
  task: Task
 ): Result<Unit> {
  return runCatching {
   dataSource.deleteTask(task)
  }
 }
}