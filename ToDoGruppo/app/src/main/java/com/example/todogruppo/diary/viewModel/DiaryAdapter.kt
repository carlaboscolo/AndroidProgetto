package com.example.todogruppo.diary.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.DiaryItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DiaryAdapter(private val diaryList: ArrayList<Diary>,
                   private val noteModel: DiaryModel,
                   private val context: Context
) : RecyclerView.Adapter<DiaryAdapter.MyViewHolder>(),
    DiaryItemTouchHelper {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.diary_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //posizione corrente della task
        val currentItem = diaryList[position]
        //data, titolo, testo
        holder.dataView.text = currentItem._data
        holder.title.text = currentItem._heading


        //selezionare una nota
        holder.itemView.setOnClickListener {
            Log.d("noteSelezionato", currentItem.toString())
            mListener?.selectItem(position)
        }

    }

    override fun getItemCount(): Int {
        //ritorna la lunghezza dell'array delle task
        return diaryList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //dove verranno visualizzate in DiaryFragment
        val dataView = itemView.findViewById<TextView>(R.id.data)
        val title = itemView.findViewById<TextView>(R.id.Heading)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    //eliminare la task
    override fun onItemDismiss(position: Int) {

        //Avviso se si vuole eliminare o no la task
        MaterialAlertDialogBuilder(context)
            .setTitle("Conferma")
            .setMessage("Vuoi eliminare questo elemento?")
            .setNegativeButton("No") { dialog, which -> }
            .setPositiveButton("Si") { dialog, which ->
                noteModel.deleteDiary(diaryList[position].id)
                diaryList.removeAt(position)
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
    }

    private var mListener: AdapterCallback? = null

    fun setOnCallback(mItemClickListener: AdapterCallback) {
        this.mListener = mItemClickListener
    }
}


