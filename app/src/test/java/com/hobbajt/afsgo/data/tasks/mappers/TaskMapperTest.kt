package com.hobbajt.afsgo.data.tasks.mappers

import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.domain.tasks.model.Task
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import org.junit.Assert
import org.junit.Test

class TaskMapperTest
{
    companion object
    {
        private val task = Task(0, "Task 1", TaskStatus.OPEN, true)
        val taskDto = TaskDTO("Task 1", TaskStatus.OPEN, 0)
    }

    @Test
    fun mapToDtoTest()
    {
        Assert.assertEquals(TaskMapper.map(task), taskDto)
    }

    @Test
    fun mapDtoTest()
    {
        Assert.assertEquals(TaskMapper.map(taskDto, true), task)
        Assert.assertEquals(TaskMapper.map(taskDto, false), task)
    }
}