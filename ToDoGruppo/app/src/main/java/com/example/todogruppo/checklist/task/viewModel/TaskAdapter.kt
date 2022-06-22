package com.example.todogruppo.checklist.task.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.NoteItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskAdapter(private val taskList: ArrayList<Task>, private val viewModel: ViewModel, private val context: Context) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>(),
    NoteItemTouchHelper {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      //posizione corrente della task
        val currentItem = taskList[position]
        //titolo, data e se la task è "check" o no
        holder.textView.text = currentItem._heading
        holder.dataView.text = currentItem._data
        holder.checkTask.isChecked = currentItem.check

        //selezionare una task
        holder.itemView.setOnClickListener {
            Log.d("taskSelezionato", currentItem.toString())
            mListener?.selectItem(position)
        }

        //inserire il check nella task
        holder.checkTask.setOnClickListener {
            currentItem.check = holder.checkTask.isChecked
            mListener?.check(position, currentItem.check)
            taskList.sortBy(Task::check)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        //ritorna la lunghezza dell'array delle task
        return taskList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       //dove verranno visualizzate in TodayFragment
        val textView = itemView.findViewById<TextView>(R.id.task)
        val dataView = itemView.findViewById<TextView>(R.id.data)
        val checkTask = itemView.findViewById<CheckBox>(R.id.todoCheckBox)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    //eliminare la task
    override fun onItemDismiss(position: Int) {
        //stampa 'oggi', 'in scadenza' o 'non in scadenza'
        Log.d("prova", "onItemDismiss")

        //Avviso se si vuole eliminare o no la task
        MaterialAlertDialogBuilder(context)
            .setTitle("Conferma")
            .setMessage("Vuoi eliminare questo elemento?")
            .setNegativeButton("No") { dialog, which -> }
            .setPositiveButton("Si") { dialog, which ->
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

    //selezionare una task
    interface AdapterCallback {
        fun selectItem(position: Int)
        fun check(position: Int, check : Boolean)
    }

    private var mListener: AdapterCallback? = null

    fun setOnCallback(mItemClickListener: AdapterCallback) {
        this.mListener = mItemClickListener
    }



}
