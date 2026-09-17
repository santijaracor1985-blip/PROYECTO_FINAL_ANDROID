package com.sena.crud.data.remote.dto

data class TaskDto(
    val id: String,
    val ownerId: String,
    val title: String,
    val description: String,
    val completed: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
