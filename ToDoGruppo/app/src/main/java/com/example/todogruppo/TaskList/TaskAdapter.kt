package com.example.ado

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lista.Task
import com.example.todogruppo.R
import com.example.todogruppo.TaskList.ItemTouchHelperAdapter

class TaskAdapter(private val taskList: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>(), ItemTouchHelperAdapter{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.textView.text = currentItem._heading
        holder.dataView.text = currentItem._data
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
        taskList.removeAt(position)
        notifyItemRemoved(position)
    }



}