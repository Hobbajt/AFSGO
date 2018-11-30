package com.hobbajt.afsgo.core.domain.usecases

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import io.reactivex.Single

abstract class SingleUseCase<T, Params>(private val schedulerProvider: SchedulerProvider)
{
    fun execute(params: Params): Single<T>
    {
        return buildUseCaseObservable(params)
                .retry(3)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
    }

    internal abstract fun buildUseCaseObservable(params: Params): Single<T>
}