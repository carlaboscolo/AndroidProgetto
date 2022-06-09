package com.example.fragment

import android.os.Bundle
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

class HomeFragment : Fragment() {

    //view model
    val viewModel: ViewModel by viewModels()

    //setta oggi come default
    private var type = TodayFragment.TYPE_TODAY

    //variabili
    private lateinit var binding: FragmentHomeBinding


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

        type = arguments?.getInt("type", TodayFragment.TYPE_DEADLINE) ?: TodayFragment.TYPE_DEADLINE

        //view model -> ottieni i dati
        viewModel.getTask(type)

       /* data di oggi
        viewModel.taskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.taskRecyclerView)

            if (it.size > 0) {
                val todayTask = binding.oggi
                todayTask.visibility = View.VISIBLE
            }
        } */

        //scadenza nella settimana
        viewModel.weekTaskList.observe(viewLifecycleOwner) {
            drawList(view, it, binding.weekRecyclerView)

            if (it.size > 0) {
                val settimana = binding.scadenzaSettimana
                settimana.visibility = View.VISIBLE
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


