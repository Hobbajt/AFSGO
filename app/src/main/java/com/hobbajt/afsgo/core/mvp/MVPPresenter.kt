package com.hobbajt.afsgo.core.mvp

import io.reactivex.disposables.CompositeDisposable

abstract class MVPPresenter<V>
{
    protected val compositeDisposable = CompositeDisposable()

    var view: V? = null

    fun attachView(view: V)
    {
        this.view = view
    }

    fun detachView()
    {
        view = null
        compositeDisposable.dispose()
    }
}

