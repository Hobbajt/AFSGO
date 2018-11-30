package com.hobbajt.afsgo.taskslist.view

import android.os.Parcelable
import com.hobbajt.afsgo.domain.tasks.model.Task
import kotlinx.android.parcel.Parcelize

/**
 * At this moment this class is useless. I created it, because in future may be necessary to add another field for save on configuration change.
 * In This way ensured is scalability and susceptibility to extension.
 */
@Parcelize
data class TasksListState(val tasks: List<Task>): Parcelable