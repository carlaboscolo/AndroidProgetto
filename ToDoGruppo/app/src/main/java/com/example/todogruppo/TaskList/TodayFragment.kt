package com.example.todogruppo.TaskList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.TaskAdapter
import com.example.lista.Task
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentTodayBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private lateinit var TaskRecyclerView: RecyclerView
    private lateinit var aggiungiTask: FloatingActionButton



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

        getTask()
    }

    fun showButton(){
        aggiungiTask.visibility = View.VISIBLE
    }

    private fun getTask(){
        val db = Firebase.firestore

        db.collection("task")
            .get()
            .addOnSuccessListener { result ->

                var taskArray = ArrayList<Task>()

                for (document in result) {
                    val task = Task(
                        document.data.getValue("name").toString(),
                        document.data.getValue("data").toString()
                    )
                    taskArray.add(task)
                }



                TaskRecyclerView = binding.taskRecyclerView
                TaskRecyclerView.setHasFixedSize(true)

                TaskRecyclerView.apply {
                  TaskRecyclerView.adapter = TaskAdapter(taskArray)
                   TaskRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
                }

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }
    }



}