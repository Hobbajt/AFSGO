package com.hobbajt.afsgo.data.tasks.local

import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import org.junit.Assert
import org.junit.Test

class TasksCreatorTest
{
    private val tasksCreator = TasksCreator()

    companion object
    {
        val tasks: List<TaskDTO> = listOf(
                TaskDTO("Task 1", TaskStatus.OPEN, 0),
                TaskDTO("Task 2", TaskStatus.OPEN, 0),
                TaskDTO("Task 3", TaskStatus.OPEN, 0),
                TaskDTO("Task 4", TaskStatus.OPEN, 0),
                TaskDTO("Task 5", TaskStatus.OPEN, 0),
                TaskDTO("Task 6", TaskStatus.OPEN, 0),
                TaskDTO("Task 7", TaskStatus.OPEN, 0),
                TaskDTO("Task 8", TaskStatus.OPEN, 0),
                TaskDTO("Task 9", TaskStatus.OPEN, 0),
                TaskDTO("Task 10", TaskStatus.OPEN, 0),
                TaskDTO("Task 11", TaskStatus.OPEN, 0),
                TaskDTO("Task 12", TaskStatus.OPEN, 0),
                TaskDTO("Task 13", TaskStatus.OPEN, 0),
                TaskDTO("Task 14", TaskStatus.OPEN, 0),
                TaskDTO("Task 15", TaskStatus.OPEN, 0),
                TaskDTO("Task 16", TaskStatus.OPEN, 0),
                TaskDTO("Task 17", TaskStatus.OPEN, 0),
                TaskDTO("Task 18", TaskStatus.OPEN, 0),
                TaskDTO("Task 19", TaskStatus.OPEN, 0),
                TaskDTO("Task 20", TaskStatus.OPEN, 0))
    }

    @Test
    fun createTest()
    {
        val createdTasks = tasksCreator.create()
        Assert.assertEquals(createdTasks, tasks)
    }
}