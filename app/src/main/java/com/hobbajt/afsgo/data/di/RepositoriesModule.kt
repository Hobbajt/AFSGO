package com.hobbajt.afsgo.data.di

import com.hobbajt.afsgo.data.tasks.local.TasksDatabase
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule
{
    @Singleton
    @Provides
    fun providesTasksRepository(tasksDatabase: TasksDatabase) = TasksRepository(tasksDatabase.taskDao())
}