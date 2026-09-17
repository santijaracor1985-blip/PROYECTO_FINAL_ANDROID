package com.sena.crud.data.local.datasource

import com.sena.crud.data.local.dao.TaskDraftDao
import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.mapper.toEntity
import com.sena.crud.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DraftLocalDataSource @Inject constructor(private val dao: TaskDraftDao) {
    fun observe(ownerId: String): Flow<List<TaskDraft>> = dao.observeDrafts(ownerId).map { list -> list.map { it.toDomain() } }
    suspend fun get(id: Int): TaskDraft? = dao.getDraft(id)?.toDomain()
    suspend fun save(draft: TaskDraft): Int = dao.insert(draft.toEntity()).toInt()
    suspend fun update(draft: TaskDraft) = dao.update(draft.toEntity())
    suspend fun delete(draft: TaskDraft) = dao.delete(draft.toEntity())
}
