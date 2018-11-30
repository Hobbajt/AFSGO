package com.hobbajt.afsgo.taskslist.view

import com.hobbajt.afsgo.core.mvp.MVPPresenter
import com.hobbajt.afsgo.domain.tasks.model.Task
import com.hobbajt.afsgo.domain.tasks.usecases.ChangeTaskStatusUseCase
import com.hobbajt.afsgo.domain.tasks.usecases.GetTasksUseCase
import javax.inject.Inject

class TasksListPresenter @Inject constructor(private val getTasksUseCase: GetTasksUseCase,
                                             private val changeTaskStatusUseCase: ChangeTaskStatusUseCase) :
        MVPPresenter<TasksListContract.View>(), TaskClickListener
{
    private var tasks = mutableListOf<Task>()
    private var isLoading = false

    // region State
    fun onSaveState(): TasksListState = TasksListState(tasks)

    fun onLoadState(tasksListState: TasksListState?)
    {
        view?.createTasksList()

        setState(tasksListState)

        loadTasksIfRequired()
    }

    private fun setState(tasksListState: TasksListState?)
    {
        if (tasksListState != null && tasksListState.tasks.isNotEmpty())
        {
            this.tasks = tasksListState.tasks.toMutableList()
        }
    }
    // endregion State

    private fun loadTasksIfRequired()
    {
        if (tasks.isNotEmpty())
        {
            view?.displayTasks(tasks)
        }
        else
        {
            loadTasks(0)
        }
    }

    // region Loaders
    private fun hideLoader()
    {
        isLoading = false
        view?.hideMainLoader()
        view?.hideLoaderItem()
    }

    private fun displayLoader()
    {
        isLoading = true
        if(tasks.isEmpty())
        {
            view?.displayMainLoader()
        }
        else
        {
            view?.displayLoaderItem()
        }
    }
    // endregion Loaders

    // region Load Tasks
    private fun loadTasks(startPosition: Int)
    {
        if(!isLoading)
        {
            displayLoader()
            val params = GetTasksUseCase.Params(startPosition)
            compositeDisposable.add(getTasksUseCase.execute(params).subscribe(
                    { onLoadTasksSuccess(it) },
                    { onLoadTasksError(it) }))
        }
    }

    private fun onLoadTasksSuccess(newTasks: List<Task>)
    {
        hideLoader()

        tasks.addAll(newTasks)
        if (tasks.size == newTasks.size)
        {
            view?.displayTasks(tasks)
        }
        else
        {
            view?.displayMoreTasks(tasks.size - newTasks.size, newTasks.size)
        }
    }

    private fun onLoadTasksError(throwable: Throwable)
    {
        hideLoader()
        throwable.printStackTrace()
    }
    // endregion Load Tasks

    // region View Actions
    fun onScrolledToEnd()
    {
        loadTasks(tasks.size)
    }

    override fun onChangeStatusClicked(position: Int)
    {
        changeTaskStatus(position)
    }
    // endregion ViewActions

    // region Change Task Status
    private fun changeTaskStatus(position: Int)
    {
        val task = tasks[position]

        val params = ChangeTaskStatusUseCase.Params(task)
        compositeDisposable.add(changeTaskStatusUseCase.execute(params)
                .subscribe({ onChangeTaskStatusSuccess(it) },
                        { it.printStackTrace() }))
    }

    private fun onChangeTaskStatusSuccess(updatedTasks: List<Task>)
    {
        tasks.clear()
        tasks.addAll(updatedTasks)
        view?.updateTasks()
    }
    // endregion Change Task Status
}
