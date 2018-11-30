package com.hobbajt.afsgo.domain.di

import com.hobbajt.afsgo.core.domain.AppSchedulerProvider
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapper
import com.hobbajt.afsgo.domain.tasks.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule
{
    @Provides
    @Singleton
    fun providesAppSchedulerProvider() = AppSchedulerProvider()

    @Provides
    @Singleton
    fun providesGetTaskUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository, isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase):
            GetTaskUseCase = GetTaskUseCase(tasksRepository, TaskMapper, isTaskStatusChangeableUseCase, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesAddTasksUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository):
            AddTasksUseCase = AddTasksUseCase(tasksRepository, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesGetTasksUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository, isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase):
            GetTasksUseCase = GetTasksUseCase(tasksRepository, TaskMapper, isTaskStatusChangeableUseCase, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesIsTaskStatusChangeableUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository):
            IsTaskStatusChangeableUseCase = IsTaskStatusChangeableUseCase(tasksRepository, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesHasAllTasksStatusUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository):
            HasAllTasksStatusUseCase = HasAllTasksStatusUseCase(tasksRepository, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesUpdateTaskStatusUseCase(appSchedulerProvider: AppSchedulerProvider, tasksRepository: TasksRepository):
            UpdateTaskUseCase = UpdateTaskUseCase(tasksRepository, TaskMapper, appSchedulerProvider)

    @Provides
    @Singleton
    fun providesChangeTaskStatusUseCase(appSchedulerProvider: AppSchedulerProvider, updateTaskUseCase: UpdateTaskUseCase, isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase, getTasksUseCase: GetTasksUseCase):
            ChangeTaskStatusUseCase = ChangeTaskStatusUseCase(TaskMapper, updateTaskUseCase, isTaskStatusChangeableUseCase, getTasksUseCase, appSchedulerProvider)

}