package com.hobbajt.afsgo.core.di.contributors

import com.hobbajt.afsgo.core.di.scopes.ActivityScope
import com.hobbajt.afsgo.main.di.MainActivityModule
import com.hobbajt.afsgo.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributorModule
{
    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentsContributorModule::class])
    @ActivityScope
    abstract fun contributeMainActivity(): MainActivity
}