package com.sena.crud.data.local.dao

import androidx.room.*
import com.sena.crud.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts WHERE ownerId = :ownerId ORDER BY savedAt DESC")
    fun observeDrafts(ownerId: String): Flow<List<TaskDraftEntity>>

    @Query("SELECT * FROM task_drafts WHERE id = :id LIMIT 1")
    suspend fun getDraft(id: Int): TaskDraftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(draft: TaskDraftEntity): Long

    @Update
    suspend fun update(draft: TaskDraftEntity)

    @Delete
    suspend fun delete(draft: TaskDraftEntity)
}
