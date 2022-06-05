package com.example.todogruppo.diary.diaryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentAddDiaryBinding
import com.example.todogruppo.diary.viewModel.Diary
import com.example.todogruppo.diary.viewModel.DiaryModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class AddDiaryFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddDiaryBinding

    //view model
    val diaryModel: DiaryModel by viewModels()

    //task null -> nuovo task, altrimenti serve per la modifica
    private var diary : Diary? = null

    //varibili
    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate: TextView
    private lateinit var titletext : EditText
    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorSave: TextView
    private lateinit var errorSave2: TextView
    private lateinit var closeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_add_diary, container, false)
        binding = FragmentAddDiaryBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se passo dei parametri la variabile note sarà valorizzata, altrimenti sarà nullo
        diary = arguments?.getSerializable("note") as Diary?

        //inizializza variabili
        addBtn = binding.newDiaryButton

        titletext = binding.titleDiary
        inputText = binding.newDiaryText
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



    }

}