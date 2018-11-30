package com.hobbajt.afsgo.domain.tasks.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import com.hobbajt.afsgo.core.domain.usecases.SingleUseCase
import com.hobbajt.afsgo.data.tasks.dto.TaskDTO
import com.hobbajt.afsgo.data.tasks.mappers.TaskMapper
import com.hobbajt.afsgo.domain.tasks.model.Task
import com.hobbajt.afsgo.domain.tasks.model.TaskStatus
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ChangeTaskStatusUseCase @Inject constructor(private val taskMapper: TaskMapper,
                                                  private val updateTaskUseCase: UpdateTaskUseCase,
                                                  private val isTaskStatusChangeableUseCase: IsTaskStatusChangeableUseCase,
                                                  private val getTasksUseCase: GetTasksUseCase,
                                                  private val schedulerProvider: SchedulerProvider) : SingleUseCase<List<Task>, ChangeTaskStatusUseCase.Params>(schedulerProvider)
{
    override fun buildUseCaseObservable(params: Params): Single<List<Task>>
    {
        return Single.just(params.task)
                .map { taskMapper.map(it) }
                .flatMapMaybe { taskDto -> getTaskIfStatusIsChangeable(taskDto) }
                .map { taskDto ->
                    taskDto.status = TaskStatus.getNext(taskDto.status)
                    taskDto
                }
                .map { taskMapper.map(it, true) }
                .concatMap { task ->
                    updateTask(task)
                            // Load 10 tasks after changed item
                            .map { task.id + 10 }
                }
                .flatMapSingle { getUpdatedTasks(it) }
    }

    private fun getTaskIfStatusIsChangeable(taskDto: TaskDTO): Maybe<TaskDTO>
    {
        return isTaskStatusChangeable(taskDto)
                .flatMapMaybe { isChangeable ->
                    if (isChangeable) Maybe.just(taskDto) else Maybe.empty()
                }
    }

    private fun isTaskStatusChangeable(taskDto: TaskDTO): Single<Boolean>
    {
        val params = IsTaskStatusChangeableUseCase.Params(taskDto)
        return isTaskStatusChangeableUseCase.execute(params)
                .observeOn(schedulerProvider.io())
    }

    private fun updateTask(task: Task): Maybe<Int>
    {
        val updateParams = UpdateTaskUseCase.Params(task)
        return updateTaskUseCase.execute(updateParams)
                .observeOn(schedulerProvider.io())
                .filter { it == 1 }
    }

    private fun getUpdatedTasks(it: Long): Single<List<Task>>
    {
        val getTasksParams = GetTasksUseCase.Params(0, it.toInt())
        return getTasksUseCase.execute(getTasksParams)
                .observeOn(schedulerProvider.io())
    }

    data class Params(val task: Task)
}