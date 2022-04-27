package com.example.viewpagerexample.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todogruppo.page.DeadlineFragment
import com.example.todogruppo.page.TodayFragment

class SlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return if(position == 0) TodayFragment() else DeadlineFragment()
    }

}