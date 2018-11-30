package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.local.TasksDAO
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import io.reactivex.Single
import javax.inject.Inject

class HasAllTasksStatusUseCase @Inject constructor(private val tasksRepository: TasksRepository,
                                                   schedulerProvider: SchedulerProvider): SingleUseCase<Boolean, HasAllTasksStatusUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<Boolean>
    {
        return tasksRepository.allTasksHasStatus(params.taskStatus)
    }

    data class Params(val taskStatus: TaskStatus)
}