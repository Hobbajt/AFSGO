package com.hobbajt.afsgo.main.di

import com.hobbajt.afsgo.core.di.scopes.ActivityScope
import com.hobbajt.afsgo.main.FragmentsChanger
import com.hobbajt.afsgo.main.view.MainActivity
import com.hobbajt.afsgo.main.view.MainPresenter
import com.hobbajt.afsgo.data.LocalPropertiesEditor
import com.hobbajt.afsgo.data.tasks.local.TasksCreator
import com.hobbajt.afsgo.domain.tasks.usecases.AddTasksUseCase
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule
{
    @Provides
    @ActivityScope
    fun providesFragmentsChanger(activity: MainActivity): FragmentsChanger = FragmentsChanger(activity.supportFragmentManager)

    @Provides
    @ActivityScope
    fun providesMainPresenter(localPropertiesEditor: LocalPropertiesEditor, addTasksUseCase: AddTasksUseCase, tasksCreator: TasksCreator): MainPresenter
            = MainPresenter(localPropertiesEditor, addTasksUseCase, tasksCreator)
}