package com.hobbajt.afsgo.taskslist.di

import com.hobbajt.afsgo.core.di.scopes.FragmentScope
import com.hobbajt.afsgo.domain.tasks.usecases.ChangeTaskStatusUseCase
import com.hobbajt.afsgo.domain.tasks.usecases.GetTasksUseCase
import com.hobbajt.afsgo.domain.tasks.usecases.UpdateTaskUseCase
import com.hobbajt.afsgo.taskslist.view.TasksListPresenter
import dagger.Module
import dagger.Provides

@Module
class TasksListModule
{
    @Provides
    @FragmentScope
    fun providesTasksListPresenter(getTasksUseCase: GetTasksUseCase, changeTaskStatusUseCase: ChangeTaskStatusUseCase):
            TasksListPresenter = TasksListPresenter(getTasksUseCase, changeTaskStatusUseCase)
}