package com.hobbajt.afsgo.core.domain;

import io.reactivex.Scheduler;

public interface SchedulerProvider
{
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
