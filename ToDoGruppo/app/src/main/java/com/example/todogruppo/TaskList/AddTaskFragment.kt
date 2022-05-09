package com.example.todogruppo.TaskList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.todogruppo.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddTaskFragment : Fragment() {

    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addBtn = view.findViewById<Button>(R.id.newTaskButton)
        val inputText = view.findViewById<EditText>(R.id.newTaskText)

        addBtn.setOnClickListener {
            /*  val value = inputText.text.toString()
            viewModel.update(value) */

         //torna indietro di un fragment
          parentFragmentManager.popBackStackImmediate()
        }

        CalendarBtn = view.findViewById(R.id.addDataBtn)
        selectedDate = view.findViewById(R.id.selected_data)

        //MaterialDatePicker
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        CalendarBtn.setOnClickListener {
            //far comparire il calendario
            datePicker.show(parentFragmentManager, "tag");

            datePicker.addOnPositiveButtonClickListener {
                // Respond to positive button click.
                selectedDate.setText(datePicker.headerText)
            }

        }

    }
}

