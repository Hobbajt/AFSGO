package com.hobbajt.afsgo.core.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import com.hobbajt.afsgo.main.FragmentsChanger
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class MVPFragment<T : MVPPresenter<*>> : DaggerFragment()
{
    @Inject
    lateinit var fragmentsChanger: FragmentsChanger

    // region Lifecycle
    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        attachView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        loadState(savedInstanceState, arguments)
    }

    override fun onDetach()
    {
        super.onDetach()
        providePresenter().detachView()
    }
    // endregion Lifecycle

    abstract fun providePresenter(): T

    protected fun goBack()
    {
        fragmentsChanger.goBack()
    }

    abstract fun loadState(configurationChangeState: Bundle?, passedArguments: Bundle?)

    abstract fun attachView()
}