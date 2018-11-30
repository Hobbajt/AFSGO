package com.hobbajt.afsgo.data.tasks.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TasksDAO
{
    @Query("SELECT * FROM tasks LIMIT :startPosition, :count")
    fun getPage(startPosition: Int, count: Int): Single<List<TaskDTO>>

    @Query("SELECT * FROM tasks WHERE tasks.id = :id")
    fun getById(id: Long): Single<TaskDTO>

    @Insert
    fun insert(vararg tasksDto: TaskDTO): List<Long>

    @Update
    fun update(task: TaskDTO): Int

    @Query("SELECT NOT EXISTS (SELECT * FROM tasks WHERE tasks.status != :taskStatus)")
    fun allTasksHasStatus(taskStatus: TaskStatus): Single<Boolean>

    @Query("SELECT * FROM tasks WHERE tasks.status != 0" )
    fun getTaskInProgress(): Maybe<TaskDTO>
}