package com.hobbajt.afsgo.domain.tasks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(val id: Long,
                val name: String,
                var status: TaskStatus,
                var isStatusChangeable: Boolean): Parcelable
{
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int
    {
        return id.hashCode()
    }
}