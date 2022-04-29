package com.example.todogruppo

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.todogruppo.TaskList.SlidePageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListTask: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = SlidePageAdapter(this)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->

            if(position == 0){
                tab.text = "Oggi"
            }else if(position == 1){
                tab.text = "In scadenza"
            }else  if(position == 2){
                tab.text = "Nessuna scadenza"
            }
            
        }.attach()

    }

}