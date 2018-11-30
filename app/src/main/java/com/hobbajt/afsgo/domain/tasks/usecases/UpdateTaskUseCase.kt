package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapper
import com.hobbajt.afsgo.domain.tasks.model.Task
import io.reactivex.Single
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository,
                                            private val taskMapper: TaskMapper,
                                            schedulerProvider: SchedulerProvider) : SingleUseCase<Int, UpdateTaskUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<Int>
    {
        return Single.just(params)
                .map { taskMapper.map(it.task) }
                .flatMap { task -> Single.fromCallable { tasksRepository.update(task) } }
    }

    data class Params(val task: Task)
}