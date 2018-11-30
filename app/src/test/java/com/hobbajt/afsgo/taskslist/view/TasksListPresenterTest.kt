package com.hobbajt.afsgo.taskslist.view

import com.hobbajt.afsgo.domain.tasks.model.Task
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import com.hobbajt.afsgo.domain.tasks.usecases.ChangeTaskStatusUseCase
import com.hobbajt.afsgo.domain.tasks.usecases.GetTasksUseCase
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TasksListPresenterTest
{
    @Mock
    lateinit var view: TasksListContract.View

    @Mock
    lateinit var getTasksUseCase: GetTasksUseCase

    @Mock
    lateinit var changeTaskStatusUseCase: ChangeTaskStatusUseCase

    private lateinit var presenter: TasksListPresenter

    companion object
    {
        private val tasks: List<Task> = listOf(Task(1,"Task 1", TaskStatus.OPEN, true),
                Task(2,"Task 2", TaskStatus.OPEN, true),
                Task(3,"Task 3", TaskStatus.OPEN, true),
                Task(4,"Task 4", TaskStatus.OPEN, true))

        private val moreTasks: List<Task> = listOf(Task(5,"Task 5", TaskStatus.OPEN, true),
                Task(6,"Task 6", TaskStatus.OPEN, true),
                Task(7,"Task 7", TaskStatus.OPEN, true),
                Task(8,"Task 8", TaskStatus.OPEN, true))

        private val tasksListStatus = TasksListState(tasks)
    }

    @Before
    fun setUp()
    {
        presenter = spy(TasksListPresenter(getTasksUseCase, changeTaskStatusUseCase))
        presenter.attachView(view)
    }

    @Test
    fun attachViewTest()
    {
        presenter.detachView()

        Assert.assertNull(presenter.view)
        presenter.attachView(view)
        Assert.assertNotNull(presenter.view)
    }

    @Test
    fun detachViewTest()
    {
        presenter.detachView()

        presenter.attachView(view)
        Assert.assertNotNull(presenter.view)

        presenter.detachView()
        Assert.assertNull(presenter.view)
    }

    @Test
    fun onLoadStateEmptyTest()
    {
        val expectedParams = GetTasksUseCase.Params(0)
        whenever(getTasksUseCase.execute(expectedParams)).thenReturn(Single.just(tasks))

        presenter.onLoadState(null)

        verify(view).createTasksList()
        verify(view).displayMainLoader()
        verify(getTasksUseCase).execute(eq(expectedParams))
        verify(view).hideMainLoader()
        verify(view).displayTasks(eq(tasks).toMutableList())
    }

    @Test
    fun onLoadStateTest()
    {
        presenter.onLoadState(tasksListStatus)

        verify(view).createTasksList()
        verify(view).displayTasks(tasks)
    }

    @Test
    fun onScrolledToEndTest()
    {
        presenter.onLoadState(tasksListStatus)
        val expectedParams = GetTasksUseCase.Params(tasks.size)
        whenever(getTasksUseCase.execute(expectedParams)).thenReturn(Single.just(moreTasks))

        presenter.onScrolledToEnd()
        verify(view).displayLoaderItem()
        verify(view).hideLoaderItem()
        verify(view).displayMoreTasks(tasks.size, moreTasks.size)
    }

    @Test
    fun onTaskClickTest()
    {
        val params = ChangeTaskStatusUseCase.Params(tasks[0])
        whenever(changeTaskStatusUseCase.execute(params)).thenReturn(Single.just(moreTasks))

        presenter.onLoadState(tasksListStatus)
        presenter.onChangeStatusClicked(0)
        verify(view).updateTasks()
    }

    @Test
    fun onSaveStateTest()
    {
        presenter.onLoadState(tasksListStatus)
        val state = presenter.onSaveState()
        Assert.assertEquals(tasksListStatus, state)
    }
}