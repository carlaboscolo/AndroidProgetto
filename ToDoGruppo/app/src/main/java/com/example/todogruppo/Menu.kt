package com.example.todogruppo

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.fragment.CalendarFragment
import com.example.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomePage : MainActivity() {
    val name = "HOME"


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        setCurrentFragment(homeFragment)

        val bottom_navigation = findViewById<NavigationBarView>(R.id.bottom_navigation)
        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.calendario -> {
                    setCurrentFragment(calendarFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }


    }


    private fun setCurrentFragment(fragment: Fragment ) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
}


