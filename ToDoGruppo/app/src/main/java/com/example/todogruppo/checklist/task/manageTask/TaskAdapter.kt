package com.example.todogruppo.checklist.task.manageTask

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
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

        holder.itemView.setOnClickListener{
            Log.d("taskSelezionato", currentItem.toString())


           /* FragmentManager.beginTransaction()
                .replace(R.id.newTaskContainer, task)
                .addToBackStack(null)
                .commit()
            */
            
           /*
            val intent = Intent(context, UpdateTask::class.java)
            intent.putExtra("name", currentItem._heading)
            context.startActivity(intent)
           */

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

}

