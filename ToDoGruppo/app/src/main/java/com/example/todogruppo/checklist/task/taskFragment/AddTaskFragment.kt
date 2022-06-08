package com.example.todogruppo.checklist.task.taskFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.viewModel.Task
import com.example.todogruppo.checklist.task.viewModel.ViewModel
import com.example.todogruppo.databinding.FragmentAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddTaskBinding

    //view model
    val viewModel: ViewModel by viewModels()

    //task null -> nuovo task, altrimenti serve per la modifica
    private var task: Task? = null

    //varibili
    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate: TextView
    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorSave: TextView
    private lateinit var closeBtn: Button


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

        //se passo dei parametri la variabile task sarà valorizzata, altrimenti sarà nullo
        task = arguments?.getSerializable("task") as Task?

        //inizializza variabili
        addBtn = binding.newTaskButton
        inputText = binding.newTaskText
        loadingView = binding.loadingView
        errorSave = binding.errorSave


        //aggiungere una data alla task
        CalendarBtn = binding.addDataBtn
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
                val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                utc.timeInMillis = it
                val format = SimpleDateFormat("dd-MM-yyyy")
                selectedDate.setText(format.format(utc.time))


            }
        }

            //salva la task inserita
            addBtn.setOnClickListener {

                loadingView.visibility = View.VISIBLE

                if (inputText.text.toString().isEmpty()) {
                    errorSave.visibility = View.VISIBLE
                    Log.d("error", "campo vuoto")
                } else {

                    if (task == null) {
                        //salva la nuova task
                        viewModel.saveTask(inputText.text.toString(), selectedDate.text.toString())
                    } else {
                        //modifica la task
                        viewModel.change(
                            task!!._id,
                            inputText.text.toString(),
                            selectedDate.text.toString()
                        )
                    }

                    //torna indietro di un fragment
                    parentFragmentManager.popBackStackImmediate()
                }

                //serve per mostrare il pulsante "+" tornando a Today Fragment
                TodayFragment.istance?.showButton()
            }


            //torna indietro senza salvare
            closeBtn = binding.closeBtn

            closeBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
                //serve per mostrare il pulsante "+" tornando a Today Fragment
                TodayFragment.istance?.showButton()
            }



        //settare le variabili per modificarle
        task?.let {
            inputText.setText(it._heading)
            selectedDate.setText(it._data)
        }

    }


}


