package com.example.todogruppo.TaskList

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todogruppo.R

class SlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return TodayFragment().apply {
            arguments = Bundle().apply {
                putInt("type", when(position){
                    0 -> TodayFragment.TYPE_TODAY
                    1 -> TodayFragment.TYPE_DEADLINE
                    else -> TodayFragment.TYPE_NO_DEADLINE
                })
            }
        }
        /* return if(position == 0) TodayFragment()
       else if(position == 1) DeadlineFragment()
        else NoDeadlineFragment()*/
    }


}


