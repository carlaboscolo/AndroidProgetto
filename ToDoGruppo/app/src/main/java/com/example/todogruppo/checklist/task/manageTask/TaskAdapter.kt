package com.example.todogruppo.checklist.task.manageTask

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.ViewModel
import com.example.todogruppo.checklist.task.manageTask.deleteTask.ItemTouchHelperAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskAdapter(private val taskList: ArrayList<Task>, private val viewModel: ViewModel, private val context: Context) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.textView.text = currentItem._heading
        holder.dataView.text = currentItem._data

        holder.textView.isChecked = currentItem.check

        //selezionare una task
        holder.itemView.setOnClickListener{
            Log.d("taskSelezionato", currentItem.toString())

            mListener?.selectItem(position)
        }

        holder.textView.setOnClickListener{
            currentItem.check = holder.textView.isChecked

            taskList.sortBy(Task::check)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<CheckBox>(R.id.todoCheckBox)
        val dataView = itemView.findViewById<TextView>(R.id.data)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemDismiss(position: Int) {
        //stampa 'oggi', 'in scandenza' o 'non in scadenza'
        Log.d("prova","onItemDismiss")
       //taskList.removeAt(position)
       //notifyItemRemoved(position)

        MaterialAlertDialogBuilder(context)
            .setTitle("Conferma")
            .setMessage("Vuoi eliminare questo elemento?")
            .setNegativeButton("No"){ dialog, which -> }
            .setPositiveButton("Si"){dialog, which ->
                viewModel.delete(taskList[position].id)
                taskList.removeAt(position)
                notifyDataSetChanged()
            }

            .show()
    }

    /*
    *
    *       Callback
    *
    * */

    interface AdapterCallback {
        fun selectItem(position: Int)
    }

    private var mListener: AdapterCallback? = null

    fun setOnCallback(mItemClickListener: AdapterCallback) {
        this.mListener = mItemClickListener
    }
}

