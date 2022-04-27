package com.example.todogruppo.lista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.todogruppo.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Lista : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = SlidePageAdapter(this)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = "Oggi ${position+1}"
        }.attach()


    }

}