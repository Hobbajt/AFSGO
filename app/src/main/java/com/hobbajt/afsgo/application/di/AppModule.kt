package com.hobbajt.afsgo.application.di

import android.arch.persistence.room.Room
import android.content.SharedPreferences
import com.hobbajt.afsgo.application.Application
import com.hobbajt.afsgo.data.LocalPropertiesEditor
import com.hobbajt.afsgo.data.tasks.local.TasksCreator
import com.hobbajt.afsgo.data.tasks.local.TasksDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule
{
    companion object
    {
        private const val DATABASE_NAME_TAG = "tasks_db"
        private const val SHARED_PREFERENCES_NAME_TAG = "applicationPrefs"
    }

    @Singleton
    @Provides
    fun providesSharedPreferences(application: Application): SharedPreferences = application.getSharedPreferences(SHARED_PREFERENCES_NAME_TAG, 0)

    @Singleton
    @Provides
    fun providesLocalPropertiesEditor(sharedPreferences: SharedPreferences) = LocalPropertiesEditor(sharedPreferences)

    @Provides
    @Singleton
    fun providesTasksCreator(): TasksCreator = TasksCreator()

    @Provides
    @Singleton
    fun providesTasksDatabase(application: Application) = Room.databaseBuilder(application.applicationContext, TasksDatabase::class.java, DATABASE_NAME_TAG)
            .build()
}