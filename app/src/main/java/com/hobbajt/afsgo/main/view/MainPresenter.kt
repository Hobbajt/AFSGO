package com.hobbajt.afsgo.main.view

import com.hobbajt.afsgo.core.mvp.MVPPresenter
import com.hobbajt.afsgo.data.LocalPropertiesEditor
import com.hobbajt.afsgo.data.tasks.local.TasksCreator
import com.hobbajt.afsgo.domain.tasks.usecases.AddTasksUseCase
import javax.inject.Inject

class MainPresenter @Inject constructor(private val localPropertiesEditor: LocalPropertiesEditor,
                                        private val addTasksUseCase: AddTasksUseCase,
                                        private val tasksCreator: TasksCreator) : MVPPresenter<MainContractor.View>()
{
    fun onViewReady()
    {
        preloadTasksIfRequired()
    }

    private fun preloadTasksIfRequired()
    {
        if (localPropertiesEditor.readIsFirstRun())
        {
            preloadTasks()
        } else
        {
            view?.start()
        }
    }

    private fun preloadTasks()
    {
        val params = AddTasksUseCase.Params(tasksCreator.create())
        compositeDisposable.add(addTasksUseCase.execute(params)
                .subscribe({
                    localPropertiesEditor.writeNotFirstRun()
                    view?.start()
                }, {
                    view?.closeApp()
                }))
    }
}