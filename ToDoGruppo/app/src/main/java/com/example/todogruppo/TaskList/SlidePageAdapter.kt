package com.example.todogruppo.TaskList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) TodayFragment()
        else if(position == 1) DeadlineFragment()
        else NoDeadlineFragment()
    }

}