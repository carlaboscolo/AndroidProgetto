package com.example.todogruppo.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.todogruppo.databinding.FragmentHomeBinding
import com.example.todogruppo.diary.diaryFragment.AddDiaryFragment
import com.example.todogruppo.diary.viewModel.Diary
import com.example.todogruppo.diary.viewModel.DiaryAdapter
import com.example.todogruppo.diary.viewModel.DiaryModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()
    val diaryModel: DiaryModel by viewModels()

    //setta oggi come default
    private var type = TodayFragment.TYPE_DEADLINE

    //dichiarazione variabili
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskToday: RecyclerView
    private lateinit var taskWeek: RecyclerView
    private lateinit var diaryDate: RecyclerView
    private lateinit var addTask: Button
    private lateinit var addDiary: Button

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
        viewModel.todayList.observe(viewLifecycleOwner) {
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


        //DIARIO
        //diary model -> ottieni i dati
        diaryModel.getDateDiary(defaultDay)

        //esegui operazioni sulla lista delle pagine di diario
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


        //apri il fragment task per aggiungere una nuova task
        addTask = binding.addBtn

        addTask.setOnClickListener {
            val task = AddTaskFragment()

            //aprire il fragment
            childFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, task)
                .addToBackStack(null)
                .commit()
        }

        //apri il fragment diario per aggiungere una nuova pagina di diario
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

    //formato della data
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

    //stampa recyclerView delle task
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

    //stampa recyclerView delle pagine di diario
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

