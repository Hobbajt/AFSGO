package com.hobbajt.afsgo.taskslist.view

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.hobbajt.afsgo.R
import com.hobbajt.afsgo.core.extensions.setVisibility
import com.hobbajt.afsgo.core.extensions.toInt
import com.hobbajt.afsgo.domain.tasks.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TasksListAdapter(private val taskClickListener: TaskClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    enum class ViewType(val id: Int)
    {
        TASK(0),
        LOADER(1);

        companion object
        {
            private val allTypes = ViewType.values().associateBy(ViewType::id)

            fun getById(id: Int): ViewType = allTypes[id] ?: LOADER
        }
    }

    private var tasks: List<Task> = emptyList()
    private var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return when (ViewType.getById(viewType))
        {
            ViewType.TASK -> TaskItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))
            ViewType.LOADER -> LoaderItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when (ViewType.getById(holder.itemViewType))
        {
            ViewType.TASK -> (holder as TaskItemHolder).bind(tasks[position])
            ViewType.LOADER -> return
        }
    }

    override fun getItemCount(): Int = tasks.size + isLoading.toInt()

    override fun getItemViewType(position: Int): Int
    {
        return if (isLoading && position == tasks.size)
        {
            ViewType.LOADER
        } else
        {
            ViewType.TASK
        }.id
    }

    // region Displaying Items
    fun displayTasks(tasks: List<Task>)
    {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun displayLoaderItem()
    {
        isLoading = true
        notifyItemInserted(tasks.size)
    }

    fun hideLoaderItem()
    {
        isLoading = false
        notifyItemRemoved(tasks.size)
    }

    fun displayMoreTasks(startPosition: Int, count: Int)
    {
        notifyItemRangeInserted(startPosition, count)
    }
    // endregion Displaying Items

    // region Holders
    inner class TaskItemHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        private val clTask: ViewGroup = view.clTask
        private val txtName: TextView = view.txtName
        private val txtId: TextView = view.txtId
        private val txtStatus: TextView = view.txtStatus
        private val btnAction: Button = view.btnAction

        fun bind(task: Task)
        {
            btnAction.setOnClickListener { taskClickListener.onChangeStatusClicked(adapterPosition) }
            txtName.text = task.name
            val idText = view.context.getString(R.string.tasks_list_item_id)
            txtId.text = String.format(idText, task.id)
            txtStatus.text = task.status.title
            btnAction.text = task.status.nextAction
            btnAction.setVisibility(task.isStatusChangeable)
            clTask.setBackgroundColor(ContextCompat.getColor(view.context, task.status.colorId))
        }
    }

    inner class LoaderItemHolder(view: View) : RecyclerView.ViewHolder(view)
    // endregion Holders
}
