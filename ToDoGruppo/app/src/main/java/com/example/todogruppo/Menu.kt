package com.example.todogruppo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fragment.CalendarFragment
import com.example.fragment.HomeFragment
import com.example.viewpagerexample.page.SlidePageAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePage : MainActivity() {
    val name = "HOME"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //fragment
        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val diaryFragment = DiaryFragment()
        val listFragment = ListFragment()
        val focusFragment = FocusFragment()

        //serve per impostare come default il fragment della home
        setCurrentFragment(homeFragment)

        val bottom_navigation = findViewById<NavigationBarView>(R.id.bottom_navigation)

        //serve per selezionare il pulsante home, appena apro il codicee
        bottom_navigation.selectedItemId = R.id.home

        //barra sopra -> titolo
        var titleUp = findViewById<MaterialToolbar>(R.id.main_toolbar)

        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.checklist -> {
                    titleUp.title = "Checklist"
                    setCurrentFragment(listFragment)
                    true
                }
                R.id.diario -> {
                    titleUp.title = "Diario"
                    setCurrentFragment(diaryFragment)
                    true
                }
                R.id.home -> {
                    titleUp.title = "Home"
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.calendario -> {
                    titleUp.title = "Calendario"
                    setCurrentFragment(calendarFragment)
                    true
                }
                R.id.focus -> {
                    titleUp.title = "Focus"
                    setCurrentFragment(focusFragment)
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


