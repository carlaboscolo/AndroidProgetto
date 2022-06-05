package com.example.todogruppo.checklist.task.deleteTask

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.checklist.note.viewModel.NoteAdapter
import com.example.todogruppo.checklist.task.viewModel.TaskAdapter
import com.example.todogruppo.diary.viewModel.DiaryAdapter

class DiarySwipeHelperCallback(val adapterDiary : DiaryAdapter) : ItemTouchHelper.Callback() {

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
        adapterDiary.onItemDismiss(viewHolder.adapterPosition)

        val position = viewHolder.adapterPosition

        // Log.d("position", Position.toString())
    }

}

