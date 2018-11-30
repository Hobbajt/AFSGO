package com.hobbajt.afsgo.data.tasks.repositories

import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.local.TasksCreatorTest
import com.hobbajt.afsgo.data.tasks.local.TasksDAO
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapperTest
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TasksRepositoryTest
{
    @Mock
    lateinit var tasksDao: TasksDAO

    private lateinit var tasksRepository: TasksRepository

    @Before
    fun setUp()
    {
        tasksDao = spy(tasksDao)
        tasksRepository = TasksRepository(tasksDao)
    }

    @Test
    fun getPageTest()
    {
        val expectedReturn = Single.just(TasksCreatorTest.tasks)
        whenever(tasksDao.getPage(0, 20))
                .thenReturn(expectedReturn)

        val result = tasksRepository.getPage(0, 20)

        Assert.assertEquals(expectedReturn, result)
    }

    @Test
    fun getByIdTest()
    {
        val expectedReturn = Single.just(TasksCreatorTest.tasks[5])
        whenever(tasksDao.getById(5))
                .thenReturn(expectedReturn)

        val result = tasksRepository.getById(5)

        Assert.assertEquals(expectedReturn, result)
    }

    @Test
    fun addTest()
    {
        val task = TasksCreatorTest.tasks[5]
        whenever(tasksDao.insert(task))
                .thenReturn(listOf(task.id))

        val result = tasksRepository.add(task)

        Assert.assertEquals(result, listOf(task.id))
    }

    @Test
    fun updateTest()
    {
        val taskDto = TaskMapperTest.taskDto
        whenever(tasksDao.update(taskDto))
                .thenReturn(1)

        val result = tasksRepository.update(taskDto)

        Assert.assertEquals(result, 1)
    }

    @Test
    fun allTasksHasStatusTest()
    {
        val expectedResult = Single.just(true)
        val taskStatus = TaskStatus.OPEN
        whenever(tasksDao.allTasksHasStatus(taskStatus))
                .thenReturn(expectedResult)

        val result = tasksRepository.allTasksHasStatus(taskStatus)

        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun getTaskInProgressTest()
    {
        val taskInProgress = Maybe.just(TaskDTO("Task 1", TaskStatus.WORKING, 0))
        whenever(tasksDao.getTaskInProgress())
                .thenReturn(taskInProgress)

        val result = tasksRepository.getTaskInProgress()

        Assert.assertEquals(taskInProgress, result)
    }
}
