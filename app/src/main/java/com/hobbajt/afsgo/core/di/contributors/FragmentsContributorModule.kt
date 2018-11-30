package com.hobbajt.afsgo.core.di.contributors

import com.hobbajt.afsgo.core.di.scopes.FragmentScope
import com.hobbajt.afsgo.taskslist.di.TasksListModule
import com.hobbajt.afsgo.taskslist.view.TasksListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class FragmentsContributorModule
{
    @ContributesAndroidInjector(modules = [TasksListModule::class])
    @FragmentScope
    abstract fun bindTasksListFragment(): TasksListFragment
}
