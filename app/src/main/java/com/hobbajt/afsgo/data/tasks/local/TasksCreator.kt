package com.hobbajt.afsgo.data.tasks.local

import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO

class TasksCreator
{
    companion object
    {
        private const val TASKS_COUNT = 20
    }

    fun create(): List<TaskDTO> = (0 until TASKS_COUNT).map { index -> TaskDTO("Task ${index + 1}", TaskStatus.OPEN) }.toList()
}