package com.example.todogruppo.checklist.task.taskFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.manageTask.ViewModel
import com.example.todogruppo.databinding.FragmentAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.protobuf.Empty

class AddTaskFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddTaskBinding

    //view model
    val viewModel: ViewModel by viewModels()

    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate: TextView

    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView : ProgressBar
    private lateinit var errorSave : TextView

    private lateinit var closeBtn : Button

    private lateinit var TaskList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_task, container, false)
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn = binding.newTaskButton
        inputText = binding.newTaskText
        loadingView = binding.loadingView
        errorSave = binding.errorSave

        //salva la task inserita
        addBtn.setOnClickListener {

            loadingView.visibility = View.VISIBLE

            if (inputText.text.toString().isEmpty()){
                errorSave.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")

            }else{
                viewModel.saveTask(inputText.text.toString(), selectedDate.text.toString())

                //torna indietro di un fragment
                parentFragmentManager.popBackStackImmediate()
            }

            TodayFragment.istance?.showButton()

        }

        //torna indietro senza salvare
        closeBtn = binding.closeBtn
        closeBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        //aggiungere una data alla task
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


