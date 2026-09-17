package com.sena.crud.data.repository

import com.sena.crud.data.remote.datasource.AuthRemoteDataSource
import com.sena.crud.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
 private val dataSource: AuthRemoteDataSource
) : AuthRepository {

 override fun currentUserId(): String? {
  return dataSource.currentUserId()
 }

 override fun currentUserEmail(): String? {
  return dataSource.currentUserEmail()
 }

 override fun isConfigured(): Boolean {
  return dataSource.isConfigured()
 }

 override suspend fun register(
  email: String,
  password: String
 ): Result<Unit> {
  return runCatching {
   dataSource.register(email, password)
  }
 }

 override suspend fun login(
  email: String,
  password: String
 ): Result<Unit> {
  return runCatching {
   dataSource.login(email, password)
  }
 }

 override fun logout() {
  dataSource.logout()
 }
}