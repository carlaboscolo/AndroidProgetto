package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.SwipeHelperCallback
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.example.todogruppo.checklist.task.viewModel.Task
import com.example.todogruppo.checklist.task.viewModel.TaskAdapter
import com.example.todogruppo.checklist.task.viewModel.ViewModel
import com.example.todogruppo.databinding.FragmentHomeBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()

    //setta oggi come default
    private var type = TodayFragment.TYPE_DEADLINE

    //variabili
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskToday: RecyclerView
    private lateinit var taskWeek : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setta oggi come default
        val defaultDay = dateFormat()
        Log.d("oggiHome", defaultDay)

        viewModel.getDateTask(defaultDay)

        //esegui operazioni sulla lista delle task
        viewModel.taskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.todayRecyclerView)

           if (it.size > 0) {
                taskToday = binding.todayRecyclerView
                binding.oggi.visibility = View.VISIBLE
           }
        }

        //view model -> ottieni i dati
        viewModel.getTask(type)

        //scadenza nella settimana
        viewModel.weekTaskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.weekRecyclerView)

            if (it.size > 0) {
                taskWeek = binding.weekRecyclerView
                binding.scadenzaSettimana.visibility = View.VISIBLE
            }
        }

    }

    fun dateFormat(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH] + 1

        val dayString = if (day < 10) "0$day" else "$day"
        val monthString = if (month < 10) "0$month" else "$month"

        val data_string = "$dayString-$monthString-$year"

        Log.d("dataHome", data_string)
        return data_string
    }

    fun drawList(view: View, taskList: ArrayList<Task>, recyclerView: RecyclerView) {

        recyclerView.setHasFixedSize(true)

        val adapter = TaskAdapter(taskList, viewModel, view.getContext())

        //carica la lista delle task
        recyclerView.apply {
            recyclerView.adapter = adapter

            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
        }

        //eliminare una task
        val callback: ItemTouchHelper.Callback = SwipeHelperCallback(adapter)
        var mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(recyclerView)
    }
}


