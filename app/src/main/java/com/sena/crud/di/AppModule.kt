package com.sena.crud.di
import android.content.Context
import androidx.room.Room
import com.sena.crud.data.local.dao.TaskDraftDao
import com.sena.crud.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule{
 @Provides @Singleton fun db(@ApplicationContext c:Context)=Room.databaseBuilder(c,AppDatabase::class.java,"task_manager.db").fallbackToDestructiveMigration().build()
 @Provides fun dao(db:AppDatabase):TaskDraftDao=db.taskDraftDao()
}
