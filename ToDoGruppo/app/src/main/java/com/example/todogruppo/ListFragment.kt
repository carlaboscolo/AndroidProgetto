package com.example.todogruppo

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.todogruppo.Note.NoteFragment
import com.example.todogruppo.TaskList.SlidePageAdapter
import com.example.todogruppo.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


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

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val adapter = SlidePageAdapter(requireActivity())
        viewPager.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->

            if(position == 0){
                tab.text = "Oggi"
            }else if(position == 1){
                tab.text = "In scadenza"
            }else  if(position == 2){
                tab.text = "Nessuna scadenza"
            }

        }.attach()



        setColorButton()

        val note = NoteFragment()

        val notebtn = view.findViewById<Button>(R.id.note)

        notebtn.setOnClickListener{

            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentNote, note)
                .addToBackStack(null)
                .commit()
        }


    }

    private fun setColorButton(){
        val notebtn = binding.includeNote.note
        notebtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.defaultBackground))
        notebtn.setTextColor(getResources().getColor(R.color.white))

        val taskbtn2 = binding.includeNote.task
        taskbtn2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Button))
        taskbtn2.setTextColor(getResources().getColor(R.color.black))
    }
}

