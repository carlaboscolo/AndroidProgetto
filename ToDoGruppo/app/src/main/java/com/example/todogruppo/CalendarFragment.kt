package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        })
    }


}

