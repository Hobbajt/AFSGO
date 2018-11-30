package com.hobbajt.afsgo.domain.tasks.model

import com.hobbajt.afsgo.R

enum class TaskStatus(val id: Int, val title: String, val nextAction: String, val colorId: Int)
{
    OPEN(0, "Open", "Start Travel", R.color.blue_gray_light),
    TRAVELLING(1, "Travelling", "Start Work", R.color.blue_gray),
    WORKING(2, "Working", "Stop", R.color.blue_gray_dark);

    /**
     * It works like a circular linked list. The Next status after the last ([WORKING]) is first ([OPEN]).
     */
    companion object
    {
        private val allStatuses = TaskStatus.values().associateBy(TaskStatus::id)

        fun getById(id: Int): TaskStatus = allStatuses[id] ?: OPEN

        fun getNext(currentStatus: TaskStatus): TaskStatus
        {
            if(isLast(currentStatus))
            {
                return getById(0)
            }
            return getById(currentStatus.id + 1)
        }

        private fun isLast(currentStatus: TaskStatus) = currentStatus.id == allStatuses.size - 1
    }
}