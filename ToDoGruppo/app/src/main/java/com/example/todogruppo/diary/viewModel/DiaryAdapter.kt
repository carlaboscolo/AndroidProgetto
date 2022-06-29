package com.example.todogruppo.diary.viewModel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.DiaryItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class DiaryAdapter(
    private val diaryList: ArrayList<Diary>,
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
        //holder.image = currentItem._imageId



     /*   val storage = FirebaseStorage.getInstance()
       // Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("gs://todogruppo.appspot.com/images/f8677525-22a4-45ac-be83-76cfb2e9a505")

        Glide.with(context)
            .load(gsReference)
            .centerCrop()
            .into(holder.image)

*/

        val storageReference = Firebase.storage
        storageReference.getReferenceFromUrl("gs://todogruppo.appspot.com/images/f8677525-22a4-45ac-be83-76cfb2e9a505")
        Glide.with(context)
            .load(storageReference)
            .centerCrop()
            .into(holder.image)


      /*
       // Create a child reference
       // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("images")

       // Child references can also take paths
       // spaceRef now points to "images/space.jpg
       // imagesRef still points to "images"
        var spaceRef = storageRef.child("images/space.jpg")

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference

        // ImageView in your Activity
         //val imageView = holder.findViewById<ImageView>(R.id.imageView)

     // Download directly from StorageReference using Glide
     // (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
                .load(storageReference)
                .into(holder.image)
*/

        //selezionare una pagina di diario
        holder.itemView.setOnClickListener {
            Log.d("Selezionato", currentItem.toString())
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
        val description = itemView.findViewById<TextView>(R.id.description)
        val image = itemView.findViewById<ImageView>(R.id.title_image)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    //eliminare la task
    override fun onItemDismiss(position: Int) {

        //Avviso se si vuole eliminare o no la pagina di diario
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


