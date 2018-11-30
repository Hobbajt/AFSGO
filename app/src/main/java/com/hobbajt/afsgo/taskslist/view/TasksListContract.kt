package com.hobbajt.afsgo.taskslist.view

import com.hobbajt.afsgo.domain.tasks.model.Task

interface TasksListContract
{
    interface View
    {
        fun createTasksList()
        fun getFirstVisibleItemPosition(): Int

        fun displayMainLoader()
        fun hideMainLoader()

        fun displayTasks(tasks: List<Task>)
        fun displayMoreTasks(startPosition: Int, count: Int)
        fun updateTasks()

        fun displayLoaderItem()
        fun hideLoaderItem()
    }
}