package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.SwipeHelperCallback
import com.example.todogruppo.checklist.task.taskFragment.AddTaskFragment
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.example.todogruppo.checklist.task.viewModel.Task
import com.example.todogruppo.checklist.task.viewModel.TaskAdapter
import com.example.todogruppo.checklist.task.viewModel.ViewModel
import com.example.todogruppo.databinding.FragmentCalendarBinding
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding

    //variabili
    private lateinit var day : CalendarView
    private lateinit var selectedDate : TextView
    private lateinit var addTask : Button
    private lateinit var taskToday : RecyclerView

    //setta oggi come default
    private var type = TodayFragment.TYPE_TODAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_calendar , container, false)
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Stampa data in base alla scelta nel calendario
        selectedDate = binding.outputDay
        day = binding.calendarView

        //data di default
        val defaultDay = viewModel.calendar()
        selectedDate.setText(defaultDay)

        day.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val day = dayOfMonth.toString()
            val year = year.toString()
            val month = month.toString()

            val dayString = if(day.toInt() < 10) "0$day" else "$day"
            val monthString = if(month.toInt() < 10) "0$month" else "$month"

            val data_string = "$dayString/$monthString/$year"

            //Log.d("data", data_string)

            selectedDate.setText(data_string)

            val checkData = "$dayString-$monthString-$year"

            if(checkData == defaultDay){

            }else{

            }

        })

        //apri il fragment task per aggiungere una nuova task
        addTask = binding.addTaskBtn

        addTask.setOnClickListener{
            /*   val task = AddTaskFragment()

            //aprire il fragment per la nuova task
            childFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, task)
                .addToBackStack(null)
                .commit()  */
        }

        //view model -> ottieni i dati
        viewModel.getTask(type)

        //esegui operazioni sulla lista delle task
        viewModel.taskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.taskRecyclerView)

            if(it.size > 0 && type == TodayFragment.TYPE_TODAY) {
            //  taskToday = binding.taskRecyclerView
            //  taskToday.visibility = View.VISIBLE
            }

        }


    }

    fun drawList(view: View, taskList: ArrayList<Task>, recyclerView: RecyclerView){

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

