package com.sena.crud.data.mapper

import com.sena.crud.data.local.entity.TaskDraftEntity
import com.sena.crud.data.remote.dto.TaskDto
import com.sena.crud.domain.model.Task
import com.sena.crud.domain.model.TaskDraft

fun TaskDraftEntity.toDomain() = TaskDraft(id, ownerId, title, description, savedAt)
fun TaskDraft.toEntity() = TaskDraftEntity(id, ownerId, title, description, savedAt)
fun TaskDto.toDomain() = Task(id, ownerId, title, description, completed, createdAt, updatedAt)
fun Task.toMap() = mapOf(
    "ownerId" to ownerId,
    "title" to title,
    "description" to description,
    "completed" to completed,
    "createdAt" to createdAt,
    "updatedAt" to updatedAt
)
