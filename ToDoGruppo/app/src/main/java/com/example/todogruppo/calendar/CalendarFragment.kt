package com.example.todogruppo.calendar

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
import com.example.todogruppo.checklist.task.deleteTask.DiarySwipeHelperCallback
import com.example.todogruppo.checklist.task.deleteTask.SwipeHelperCallback
import com.example.todogruppo.checklist.task.taskFragment.AddTaskFragment
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.example.todogruppo.checklist.task.viewModel.Task
import com.example.todogruppo.checklist.task.viewModel.TaskAdapter
import com.example.todogruppo.checklist.task.viewModel.ViewModel
import com.example.todogruppo.databinding.FragmentCalendarBinding
import com.example.todogruppo.diary.diaryFragment.AddDiaryFragment
import com.example.todogruppo.diary.viewModel.Diary
import com.example.todogruppo.diary.viewModel.DiaryAdapter
import com.example.todogruppo.diary.viewModel.DiaryModel
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()
    val diaryModel: DiaryModel by viewModels()

    private lateinit var binding: FragmentCalendarBinding

    //dichiarazione variabili
    private lateinit var day: CalendarView
    private lateinit var selectedDate: TextView
    private lateinit var addTask: Button
    private lateinit var addDiary: Button
    private lateinit var taskToday: RecyclerView
    private lateinit var diaryDate: RecyclerView
    private var data_string = " "

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

        //setta oggi come default
        val defaultDay = dateFormat()
        selectedDate.setText(defaultDay)
        Log.d("oggi", defaultDay)

        //selezionare una data nel calendario
        day.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val day = dayOfMonth.toString()
            val year = year.toString()
            val month = month + 1
            month.toString()

            //inserisci uno 0 se è una data < 10 di giorno o mese
            val dayString = if (day.toInt() < 10) "0$day" else "$day"
            val monthString = if (month.toInt() < 10) "0$month" else "$month"

            //formato di stampa a schermo della data
            data_string = "$dayString/$monthString/$year"
            Log.d("data", data_string)

            //inserisci data selezionata come titolo
            selectedDate.setText(data_string)

            //formato data uguale al db
            val checkData = "$dayString-$monthString-$year"

            //view model -> ottieni i dati in base ad una data
            viewModel.getDateTask(checkData)

            //esegui operazioni sulla lista delle task
            viewModel.todayList.observe(viewLifecycleOwner) {
                drawList(view, it, binding.taskRecyclerView)
                taskToday = binding.taskRecyclerView
                taskToday.visibility = View.VISIBLE
            }

            //DIARIO
            //diary model -> ottieni i dati
            diaryModel.getDateDiary(checkData)

            //esegui operazioni sulla lista delle task
            diaryModel.diaryList.observe(viewLifecycleOwner) {
                drawDiary(view, it, binding.diaryRecyclerView)

                if (it.size > 0) {
                    diaryDate = binding.diaryRecyclerView
                    diaryDate.visibility = View.VISIBLE
                    binding.avvisoDiario.visibility = View.GONE
                    binding.diarioBtn.visibility = View.GONE
                } else {
                    binding.avvisoDiario.visibility = View.VISIBLE
                    binding.diarioBtn.visibility = View.VISIBLE
                }
            }
        })


        //apri il fragment task per aggiungere una nuova task
        addTask = binding.addTaskBtn

        addTask.setOnClickListener {
            val task = AddTaskFragment()

            //aprire il fragment
            childFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, task)
                .addToBackStack(null)
                .commit()
        }

        //apri il fragment task per aggiungere una nuova pagina di diario
        addDiary = binding.diarioBtn

        addDiary.setOnClickListener {
            val diary = AddDiaryFragment()

            //aprire il fragment
            childFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, diary)
                .addToBackStack(null)
                .commit()
        }

    }

    fun dateFormat(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH] + 1

        val dayString = if (day < 10) "0$day" else "$day"
        val monthString = if (month < 10) "0$month" else "$month"

        val data_string = "$dayString/$monthString/$year"

        Log.d("data", data_string)
        return data_string
    }

    fun drawList(view: View, taskList: ArrayList<Task>, recyclerView: RecyclerView) {

        recyclerView.setHasFixedSize(true)

        val adapter = TaskAdapter(taskList, viewModel, view.getContext())

        adapter.setOnCallback(object : TaskAdapter.AdapterCallback {

            //cliccare per modificare
            override fun selectItem(position: Int) {
                val task = AddTaskFragment()
                val bundle = Bundle()

                //bundle serve per passare i parametri nei fragment
                bundle.putSerializable("task", taskList[position])

                task.arguments = bundle

                //aprire il fragment new task
                childFragmentManager.beginTransaction()
                    .replace(R.id.containerFragment, task)
                    .addToBackStack(null)
                    .commit()
            }

            override fun check(position: Int, check: Boolean) {
                viewModel.changeCheck(taskList.get(position).id, check)
            }
        })

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


    fun drawDiary(view: View, diaryList: ArrayList<Diary>, DiaryRecyclerView: RecyclerView) {

        DiaryRecyclerView.setHasFixedSize(true)

        val adapterDiary = DiaryAdapter(diaryList, diaryModel, view.getContext())

        adapterDiary.setOnCallback(object : DiaryAdapter.AdapterCallback {

            //cliccare per modificare
            override fun selectItem(position: Int) {

                val diary = AddDiaryFragment()
                val bundle = Bundle()

                //bundle serve per passare i parametri nei fragment
                bundle.putSerializable("diary", diaryList[position])

                diary.arguments = bundle

                //aprire il fragment new diary
                childFragmentManager.beginTransaction()
                    .replace(R.id.containerFragment, diary)
                    .addToBackStack(null)
                    .commit()
            }

        })


        //carica la lista delle pagine di diario
        DiaryRecyclerView.apply {
            DiaryRecyclerView.adapter = adapterDiary

            DiaryRecyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
        }

        //eliminare una giornata di diario
        val callback: ItemTouchHelper.Callback = DiarySwipeHelperCallback(adapterDiary)
        var mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(DiaryRecyclerView)
    }
}
