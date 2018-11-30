package com.hobbajt.afsgo.data.tasks.local

import android.arch.persistence.room.TypeConverter
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus

class TaskStatusConverter
{
    @TypeConverter
    fun toTaskStatus(taskStatusId: Int): TaskStatus = TaskStatus.getById(taskStatusId)

    @TypeConverter
    fun toId(taskStatus: TaskStatus): Int = taskStatus.id
}