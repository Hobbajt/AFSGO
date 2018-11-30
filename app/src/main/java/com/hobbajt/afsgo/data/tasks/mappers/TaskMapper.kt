package com.hobbajt.afsgo.data.tasks.mappers

import com.hobbajt.afsgo.domain.tasks.model.Task
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO

object TaskMapper
{
    fun map(taskDto: TaskDTO, isStatusChangeable: Boolean): Task = Task(taskDto.id, taskDto.name, taskDto.status, isStatusChangeable)

    fun map(task: Task): TaskDTO = TaskDTO( task.name, task.status, task.id)
}