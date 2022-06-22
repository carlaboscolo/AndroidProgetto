package com.example.todogruppo.checklist.task.taskFragment

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
import com.example.todogruppo.checklist.task.viewModel.TaskAdapter
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.deleteTask.NoteSwipeHelperCallback
import com.example.todogruppo.checklist.task.deleteTask.SwipeHelperCallback
import com.example.todogruppo.checklist.task.viewModel.Task
import com.example.todogruppo.checklist.task.viewModel.ViewModel
import com.example.todogruppo.databinding.FragmentTodayBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


open class TodayFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()

    //setta oggi come default
    private var type = TYPE_TODAY

    //variabili
    private lateinit var binding: FragmentTodayBinding
    private lateinit var aggiungiTask: FloatingActionButton

    //TodayFragment gestirà "oggi", "in scadenza" e "nessuna scadenza"
    companion object {
        var istance: TodayFragment? = null

        const val TYPE_TODAY = 0
        const val TYPE_DEADLINE = 1
        const val TYPE_NO_DEADLINE = 2
    }

    init {
        istance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_today, container, false)
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //type -> settata ad "oggi"
        type = arguments?.getInt("type", TYPE_DEADLINE) ?: TYPE_DEADLINE


        //bottone che serve ad aggiungere una task
        aggiungiTask = binding.includeBtn.addNewBtn

        aggiungiTask.setOnClickListener {
            //quando viene cliccato, il bottone "+" scompare perchè non si deve vedere in "addTask"
            aggiungiTask.visibility = View.GONE

            val task = AddTaskFragment()

            //aprire il fragment per la nuova task
            childFragmentManager.beginTransaction()
                .replace(R.id.newTaskContainer, task)
                .addToBackStack(null)
                .commit()
        }

        //view model -> ottieni i dati
        viewModel.getTask(type)

        //esegui operazioni sulla lista delle task

        //scaduti
        viewModel.taskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.taskRecyclerView)

            if(it.size > 0 && type == TYPE_DEADLINE){
                val scaduti  = binding.scaduti
                scaduti.visibility = View.VISIBLE
            }
        }

        //scadenza nella settimana
        viewModel.weekTaskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.weekRecyclerView)

            if(it.size > 0){
                val settimana = binding.scadenzaSettimana
                settimana.visibility = View.VISIBLE
            }

        }

        //scadenza oltre la settimana
        viewModel.otherTaskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.otherWeekRecyclerView)

            if(it.size > 0) {
                val oltreSettimana = binding.oltreSettimana
                oltreSettimana.visibility = View.VISIBLE
            }
        }
    }

     fun drawList(view: View, taskList: ArrayList<Task>, recyclerView: RecyclerView){

        recyclerView.setHasFixedSize(true)

        val adapter = TaskAdapter(taskList, viewModel, view.getContext())

        adapter.setOnCallback(object : TaskAdapter.AdapterCallback {

            //cliccare per modificare
            override fun selectItem(position: Int) {
                //nasconde il pulsante "+" quando apro "addTask"
                aggiungiTask.visibility = View.GONE

                val task = AddTaskFragment()
                val bundle = Bundle()

                //bundle serve per passare i parametri nei fragment
                bundle.putSerializable("task", taskList[position])

                task.arguments = bundle

                //aprire il fragment new task
                childFragmentManager.beginTransaction()
                    .replace(R.id.newTaskContainer, task)
                    .addToBackStack(null)
                    .commit()
            }

            override fun check(position: Int, check : Boolean) {
                viewModel.changeCheck(taskList.get(position).id,check)
                Log.d("check", check.toString())
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
    //togliere il bottone dal fragment figlio
    fun showButton() {
        aggiungiTask.visibility = View.VISIBLE
    }

}