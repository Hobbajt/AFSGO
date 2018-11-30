package com.hobbajt.afsgo.data.tasks.repositories

import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.local.TasksDAO
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * At this moment, this class is useless. I created it, because in future may be necessary to add another data source (eg. REST API, storage).
 * In This way ensured is scalability and susceptibility to extension.
 */
class TasksRepository @Inject constructor(private val tasksDao: TasksDAO)
{
    fun getPage(startPosition: Int, count: Int): Single<List<TaskDTO>> = tasksDao.getPage(startPosition, count)

    fun getById(id: Long): Single<TaskDTO> = tasksDao.getById(id)

    fun add(vararg tasksDto: TaskDTO): List<Long> = tasksDao.insert(*tasksDto)

    fun update(task: TaskDTO): Int = tasksDao.update(task)

    fun allTasksHasStatus(taskStatus: TaskStatus): Single<Boolean> = tasksDao.allTasksHasStatus(taskStatus)

    fun getTaskInProgress(): Maybe<TaskDTO> = tasksDao.getTaskInProgress()
}