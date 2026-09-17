package com.sena.crud.domain.repository

import com.sena.crud.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    fun observeDrafts(ownerId: String): Flow<List<TaskDraft>>
    suspend fun getDraft(id: Int): TaskDraft?
    suspend fun saveDraft(draft: TaskDraft): Result<Int>
    suspend fun updateDraft(draft: TaskDraft): Result<Unit>
    suspend fun deleteDraft(draft: TaskDraft): Result<Unit>
}
