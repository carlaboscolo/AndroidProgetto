package com.example.todogruppo.Note

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.todogruppo.ListFragment
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentListBinding
import com.example.todogruppo.databinding.FragmentNoteBinding

open class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_note, container, false)
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setColorButton()

        val taskbtn = view.findViewById<Button>(R.id.task)

        taskbtn.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }


    }



    private fun setColorButton(){
        val notebtn = binding.includeNote.note
        notebtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Button))
        notebtn.setTextColor(getResources().getColor(R.color.black));

        val taskbtn = binding.includeNote.task
        taskbtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.defaultBackground))
        taskbtn.setTextColor(getResources().getColor(R.color.white));
    }

}