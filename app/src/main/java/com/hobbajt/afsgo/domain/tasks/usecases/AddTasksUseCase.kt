package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.CompletableUseCase
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.repositories.TasksRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository,
                                          schedulerProvider: SchedulerProvider): CompletableUseCase<AddTasksUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Completable
    {
        return Completable.fromAction { tasksRepository.add(*params.tasksDto.toTypedArray()) }
    }
    data class Params(val tasksDto: List<TaskDTO>)
}