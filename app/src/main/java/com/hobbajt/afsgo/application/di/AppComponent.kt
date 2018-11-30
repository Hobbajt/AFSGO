package com.hobbajt.afsgo.application.di

import com.hobbajt.afsgo.application.Application
import com.hobbajt.afsgo.core.di.contributors.ActivityContributorModule
import com.hobbajt.afsgo.data.di.RepositoriesModule
import com.hobbajt.afsgo.domain.di.UseCasesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityContributorModule::class, UseCasesModule::class, RepositoriesModule::class])
interface AppComponent
{
    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: Application)
}