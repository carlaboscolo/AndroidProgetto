package com.example.todogruppo.TaskList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.todogruppo.R

class AddTaskFragment : Fragment() {

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
            parentFragmentManager.popBackStack()
        }
    }
}

