package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapper
import com.hobbajt.afsgo.domain.tasks.model.Task
import io.reactivex.Single
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository,
                                         private val taskMapper: TaskMapper,
                                         private val isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase,
                                         private val schedulerProvider: SchedulerProvider) : SingleUseCase<Task, GetTaskUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<Task>
    {
        return tasksRepository.getById(params.id)
                .flatMap { task ->
                    isTaskStatusChangeable(task)
                            .map { taskMapper.map(task, it) }
                }
    }

    private fun isTaskStatusChangeable(task: TaskDTO): Single<Boolean>
    {
        val params = IsTaskStatusChangeableUseCase.Params(task)
        return isTaskStatusChangeableUseCase.execute(params)
                .observeOn(schedulerProvider.io())
    }

    data class Params(val id: Long)
}