package com.example.todogruppo.TaskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.MyAdapter
import com.example.lista.Task
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentTodayBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class TodayFragment : Fragment() {


    private lateinit var binding: FragmentTodayBinding

    private lateinit var TaskRecyclerView: RecyclerView
    private lateinit var TaskArrayList: ArrayList<Task>
    lateinit var heading: Array<String>
  //  lateinit var data : Array<DatePicker>

    private lateinit var aggiungiTask: FloatingActionButton

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
        //aggiungiTask.visibility = View.VISIBLE


    aggiungiTask.setOnClickListener {
        //aggiungere task

       childFragmentManager.beginTransaction()
           .replace(R.id.newTaskContainer, task)
           .addToBackStack(null)
           .commit()
    }




    heading = arrayOf(
    "PROVA 1",
    "PROVA 2",
    "PROVA 3",
    "PROVA 4"
    )

        TaskRecyclerView = view.findViewById(R.id.taskRecyclerView)
        //TaskRecyclerView.layoutManager = LinearLayoutManager(this)
        TaskRecyclerView.setHasFixedSize(true)
        TaskArrayList = arrayListOf<Task>()

        getUserdata()
    }

    private fun getUserdata() {

        for (i in heading.indices) {
            val taskElement = Task(heading[i])
            //, data[i]
            TaskArrayList.add(taskElement)
        }

        TaskRecyclerView.adapter = MyAdapter(TaskArrayList)
    }



}