package com.hobbajt.afsgo

import com.hobbajt.afsgo.core.domain.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val testScheduler: TestScheduler) : SchedulerProvider
{
    override fun ui(): Scheduler = testScheduler

    override fun computation(): Scheduler = testScheduler

    override fun io(): Scheduler = testScheduler
}
