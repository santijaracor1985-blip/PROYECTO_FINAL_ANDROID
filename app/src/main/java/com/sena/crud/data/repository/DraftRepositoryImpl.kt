package com.sena.crud.data.repository

import com.sena.crud.data.local.datasource.DraftLocalDataSource
import com.sena.crud.domain.model.TaskDraft
import com.sena.crud.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
 private val dataSource: DraftLocalDataSource
) : DraftRepository {

 override fun observeDrafts(
  ownerId: String
 ): Flow<List<TaskDraft>> {
  return dataSource.observe(ownerId)
 }

 override suspend fun getDraft(
  id: Int
 ): TaskDraft? {
  return dataSource.get(id)
 }

 override suspend fun saveDraft(
  draft: TaskDraft
 ): Result<Int> {
  return runCatching {
   dataSource.save(draft)
  }
 }

 override suspend fun updateDraft(
  draft: TaskDraft
 ): Result<Unit> {
  return runCatching {
   dataSource.update(draft)
  }
 }

 override suspend fun deleteDraft(
  draft: TaskDraft
 ): Result<Unit> {
  return runCatching {
   dataSource.delete(draft)
  }
 }
}