package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.local.TasksDAO
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import io.reactivex.Single

class IsTaskStatusChangeableUseCase(private val tasksRepository: TasksRepository,
                                    schedulerProvider: SchedulerProvider): SingleUseCase<Boolean, IsTaskStatusChangeableUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<Boolean>
    {
        return tasksRepository.getTaskInProgress()
                .map { taskInProgress -> params.taskDto.id == taskInProgress.id }
                .toSingle(true)
    }

    data class Params(val taskDto: TaskDTO)
}