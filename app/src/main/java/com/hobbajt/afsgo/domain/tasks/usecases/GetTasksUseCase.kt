package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapper
import com.hobbajt.afsgo.domain.tasks.model.Task
import io.reactivex.Single
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository,
                                          private val taskMapper: TaskMapper,
                                          private val isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase,
                                          private val schedulerProvider: SchedulerProvider) : SingleUseCase<List<Task>, GetTasksUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<List<Task>>
    {
        return tasksRepository.getPage(params.startPosition, params.count)
                .doOnSuccess()
                {
                    require(it.isNotEmpty()) { "API returned no tasks!" }
                }
                .flattenAsObservable { it }
                .concatMap { task -> isTaskStatusChangeable(task)
                        .map { taskMapper.map(task, it) }
                        .toObservable()}
                .toList()
    }

    private fun isTaskStatusChangeable(task: TaskDTO): Single<Boolean>
    {
        val params = IsTaskStatusChangeableUseCase.Params(task)
        return isTaskStatusChangeableUseCase.execute(params)
                .observeOn(schedulerProvider.io())
    }

    data class Params(val startPosition: Int, val count: Int = 10)
}