package com.example.todogruppo.diary.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.DiaryItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DiaryAdapter(private val diaryList: ArrayList<Diary>,
                   private val diaryModel: DiaryModel,
                   private val context: Context
) : RecyclerView.Adapter<DiaryAdapter.MyViewHolder>(),
    DiaryItemTouchHelper {

    //firebase storage per le foto
   /* private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    */


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
        holder.description.text = currentItem._description
        //currentItem._imageId

        //selezionare una pagina di diario
        holder.itemView.setOnClickListener {
            Log.d("Selezionato", currentItem.toString())
            mListener?.selectItem(position)
        }

      /*  var imageReference = storageReference?.child("images/_imageId")

        val ONE_MEGABYTE: Long = 1024 * 1024
        if (imageReference != null) {
            imageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                // Data for "images/island.jpg" is returned, use this as needed
                holder.image
            }.addOnFailureListener {
                // Handle any errors
            }
        }

       */

    }

    override fun getItemCount(): Int {
        //ritorna la lunghezza dell'array delle task
        return diaryList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //dove verranno visualizzate in DiaryFragment
        val dataView = itemView.findViewById<TextView>(R.id.data)
        val title = itemView.findViewById<TextView>(R.id.Heading)
        val description = itemView.findViewById<TextView>(R.id.description)
        val image = itemView.findViewById<ImageView>(R.id.title_image)
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
                diaryModel.deleteDiary(diaryList[position].id)
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


