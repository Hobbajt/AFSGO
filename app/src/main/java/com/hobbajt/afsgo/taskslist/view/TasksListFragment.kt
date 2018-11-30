package com.hobbajt.afsgo.taskslist.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hobbajt.afsgo.R
import com.hobbajt.afsgo.core.mvp.MVPFragment
import com.hobbajt.afsgo.domain.tasks.model.Task
import kotlinx.android.synthetic.main.fragment_list_tasks.*
import javax.inject.Inject


class TasksListFragment : MVPFragment<TasksListPresenter>(), TasksListContract.View
{
    @Inject
    lateinit var presenter: TasksListPresenter

    private var tasksAdapter: TasksListAdapter? = null

    override fun attachView()
    {
        presenter.attachView(this)
    }

    override fun providePresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_list_tasks, container, false)
    }

    // region Tasks List
    override fun createTasksList()
    {
        rvTasks.layoutManager = LinearLayoutManager(activity)
        rvTasks.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)
            {
                val totalItemCount = rvTasks.layoutManager.itemCount
                val lastVisibleItem = (rvTasks.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (dy > 0 && totalItemCount <= lastVisibleItem + 1)
                    presenter.onScrolledToEnd()
            }
        })
        tasksAdapter = TasksListAdapter(presenter)
        rvTasks.adapter = tasksAdapter
    }

    override fun getFirstVisibleItemPosition() = (rvTasks.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition() ?: 0
    // endregion Tasks List

    override fun onDestroy()
    {
        super.onDestroy()
        //Prevent memory leaks
        rvTasks?.adapter = null
    }

    // region Displaying Items
    override fun displayLoaderItem()
    {
        tasksAdapter?.displayLoaderItem()
    }

    override fun hideLoaderItem()
    {
        tasksAdapter?.hideLoaderItem()
    }

    override fun displayTasks(tasks: List<Task>)
    {
        tasksAdapter?.displayTasks(tasks)
    }

    override fun displayMoreTasks(startPosition: Int, count: Int)
    {
        tasksAdapter?.displayMoreTasks(startPosition, count)
    }

    override fun updateTasks()
    {
        tasksAdapter?.notifyDataSetChanged()
    }
    // endregion Displaying Items

    // region State
    override fun loadState(configurationChangeState: Bundle?, passedArguments: Bundle?)
    {
        var tasksListState = configurationChangeState?.getParcelable<TasksListState>(TASKS_LIST_STATE_TAG)
        if (tasksListState == null)
        {
            tasksListState = passedArguments?.getParcelable(TASKS_LIST_STATE_TAG)
        }

        presenter.onLoadState(tasksListState)
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        val state = presenter.onSaveState()
        outState.putParcelable(TASKS_LIST_STATE_TAG, state)
    }
    // endregion State

    // region Main Loader
    override fun displayMainLoader()
    {
        pbLoader.visibility = View.VISIBLE
    }

    override fun hideMainLoader()
    {
        pbLoader.visibility = View.GONE
    }
    // endregion Main Loader

    companion object
    {
        const val TASKS_LIST_STATE_TAG = "tasksListStateTag"

        fun newInstance() = TasksListFragment()
    }
}