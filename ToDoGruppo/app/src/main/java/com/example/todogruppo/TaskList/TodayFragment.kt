package com.example.todogruppo.TaskList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.MyAdapter
import com.example.lista.Task
import com.example.todogruppo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodayFragment : Fragment() {

    private lateinit var TaskRecyclerView: RecyclerView
    private lateinit var TaskArrayList: ArrayList<Task>
    lateinit var heading: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    val task = AddTaskFragment()
    val aggiungiTask = view.findViewById<FloatingActionButton>(R.id.add)

    aggiungiTask.setOnClickListener {
        //aggiungere task
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
            TaskArrayList.add(taskElement)
        }

        TaskRecyclerView.adapter = MyAdapter(TaskArrayList)
    }


}