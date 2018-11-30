package com.hobbajt.afsgo.data.tasks.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO

@Database(entities = [TaskDTO::class], version = 1, exportSchema = false)
@TypeConverters(TaskStatusConverter::class)
abstract class TasksDatabase : RoomDatabase()
{
    abstract fun taskDao(): TasksDAO
}