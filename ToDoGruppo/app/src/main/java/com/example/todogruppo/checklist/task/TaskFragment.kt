package com.example.todogruppo.checklist.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.todogruppo.databinding.FragmentTaskBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    //variabili
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_task, container, false)
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //il view pager serve per mostrare diverse schermate a seconda di cosa viene cliccato nel tabLayout
        viewPager = binding.viewPager

        val adapter = SlidePageAdapter(requireActivity())
        viewPager.adapter = adapter

        //menu sopra (oggi, in scadenza, nessuna scadenza)
        tabLayout = binding.tabLayout

        //menu in base alla posizione apre una parte diversa (viene gestita da slide page adapter)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            if (position == 0) {
                tab.text = "Oggi"
            } else if (position == 1) {
                tab.text = "In scadenza"
            } else if (position == 2) {
                tab.text = "Nessuna scadenza"
            }

        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TaskFragment()
    }
}