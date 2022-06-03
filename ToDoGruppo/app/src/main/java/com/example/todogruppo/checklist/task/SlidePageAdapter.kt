package com.example.todogruppo.checklist.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment

class SlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    //quante colonne nel menù deve tornare
    override fun getItemCount(): Int {
        return 3
    }

    //quali "tipi" di fragment deve mostrare a seconda della posizione
    override fun createFragment(position: Int): Fragment {
        return TodayFragment().apply {
            arguments = Bundle().apply {
                putInt(
                    "type", when (position) {
                        0 -> TodayFragment.TYPE_TODAY
                        1 -> TodayFragment.TYPE_DEADLINE
                        else -> TodayFragment.TYPE_NO_DEADLINE
                    }
                )
            }
        }

    }

}
