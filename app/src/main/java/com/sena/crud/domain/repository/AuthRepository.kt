package com.sena.crud.domain.repository

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    fun currentUserId(): String?
    fun currentUserEmail(): String?
    fun isConfigured(): Boolean
    fun logout()
}
