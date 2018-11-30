package com.hobbajt.afsgo.data.tasks.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import com.hobbajt.afsgo.data.tasks.local.TaskStatusConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class TaskDTO(
        val name: String,

        @TypeConverters(TaskStatusConverter::class)
        var status: TaskStatus,

        @PrimaryKey(autoGenerate = true)
        val id: Long = 0) : Parcelable