package com.sena.crud.di
import com.sena.crud.data.repository.*
import com.sena.crud.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module @InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
 @Binds @Singleton abstract fun auth(i:AuthRepositoryImpl):AuthRepository
 @Binds @Singleton abstract fun tasks(i:TaskRepositoryImpl):TaskRepository
 @Binds @Singleton abstract fun drafts(i:DraftRepositoryImpl):DraftRepository
}
