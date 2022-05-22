package com.example.todogruppo.checklist

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.todogruppo.checklist.note.NoteFragment
import com.example.todogruppo.R
import com.example.todogruppo.checklist.task.TaskFragment
import com.example.todogruppo.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_list, container, false)

        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showTask()

        //Note
        val notebtn = view.findViewById<Button>(R.id.note)
        notebtn.setOnClickListener{
            selectBtnNote()
            showNote()
        }

        //Task
        val taskbtn = view.findViewById<Button>(R.id.task)
        taskbtn.setOnClickListener {
            selectBtnTask()
            showTask()
        }
    }

    private fun showNote(){
        val note = NoteFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentNote, note)
            .addToBackStack(null)
            .commit()
    }

    private fun showTask(){
        val task = TaskFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentNote, task)
            .addToBackStack(null)
            .commit()
    }


    private fun selectBtnNote(){
        val notebtn = binding.includeNote.note
        notebtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Button))
        notebtn.setTextColor(getResources().getColor(R.color.black))

        val taskbtn2 = binding.includeNote.task
        taskbtn2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.defaultBackground))
        taskbtn2.setTextColor(getResources().getColor(R.color.white))
    }

    private fun selectBtnTask(){
        val notebtn = binding.includeNote.note
        notebtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.defaultBackground))
        notebtn.setTextColor(getResources().getColor(R.color.white))

        val taskbtn2 = binding.includeNote.task
        taskbtn2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Button))
        taskbtn2.setTextColor(getResources().getColor(R.color.black))
    }


}

