package com.example.todogruppo.TaskList

import android.content.ContentValues
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.TaskAdapter
import com.example.lista.Task
import com.example.todogruppo.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.FieldPosition

class SwipeHelperCallback(val adapter : TaskAdapter): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        //val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = ItemTouchHelper.START
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)

        val position = viewHolder.adapterPosition

        // Log.d("ciao", Position.toString())
    }



}

