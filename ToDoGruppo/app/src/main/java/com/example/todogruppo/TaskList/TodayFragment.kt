package com.example.todogruppo.TaskList

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.TaskAdapter
import com.example.lista.Task
import com.example.todogruppo.R
import com.example.todogruppo.ViewModel
import com.example.todogruppo.databinding.FragmentTodayBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class TodayFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()

    private lateinit var binding: FragmentTodayBinding
    private lateinit var TaskRecyclerView: RecyclerView
    private lateinit var aggiungiTask: FloatingActionButton

    //togliere il bottone dal fragment figlio
    companion object{
        var istance: TodayFragment? = null
    }

    init{
        istance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_today, container, false)

        binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = AddTaskFragment()

        aggiungiTask = view.findViewById(R.id.addTask)



        aggiungiTask.setOnClickListener {
            aggiungiTask.visibility = View.GONE

            //aggiungere task
            childFragmentManager.beginTransaction()
                .replace(R.id.newTaskContainer, task)
                .addToBackStack(null)
                .commit()
        }

        viewModel.getTask()

        viewModel.taskList.observe(viewLifecycleOwner){
            TaskRecyclerView = binding.taskRecyclerView
            TaskRecyclerView.setHasFixedSize(true)

            TaskRecyclerView.apply {
                TaskRecyclerView.adapter = TaskAdapter(it)
                TaskRecyclerView.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false)
            }


            val callback: ItemTouchHelper.Callback = SwipeHelperCallback(TaskAdapter(it))
            var mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper?.attachToRecyclerView(TaskRecyclerView)
        }

    }

    fun showButton(){
        aggiungiTask.visibility = View.VISIBLE
    }


}