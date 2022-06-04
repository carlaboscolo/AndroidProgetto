package com.example.todogruppo.checklist.note.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.NoteItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAdapter(private val noteList: ArrayList<Note>,
    private val noteModel: NoteModel,
    private val context: Context
) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>(),
    NoteItemTouchHelper {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //posizione corrente della task
        val currentItem = noteList[position]
        //titolo
        holder.title.text = currentItem._heading
        holder.textNote.text = currentItem._description

        //selezionare una nota
        holder.itemView.setOnClickListener {
            Log.d("noteSelezionato", currentItem.toString())
            mListener?.selectItem(position)
        }

    }

    override fun getItemCount(): Int {
        //ritorna la lunghezza dell'array delle task
        return noteList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //dove verranno visualizzate in TodayFragment
        val title = itemView.findViewById<TextView>(R.id.titleText)
        val textNote = itemView.findViewById<TextView>(R.id.noteText)
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
                noteModel.deleteNote(noteList[position].id)
                noteList.removeAt(position)
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

