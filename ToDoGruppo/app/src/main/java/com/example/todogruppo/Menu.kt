package com.example.todogruppo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fragment.CalendarFragment
import com.example.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomePage : MainActivity() {
    val name = "HOME"





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val homeFragment = HomeFragment()
        val calendarFragment  = CalendarFragment()
        setCurrentFragment(homeFragment)

        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.calendario -> {
                    setCurrentFragment(calendarFragment)
                    true
                }
                else -> {

                    supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, HomeFragment.newInstance("", ""))
                    .commit()

                false
                }
            }

        }

             /* when (item.itemId) {
                   R.id.home -> {
                    // Respond to navigation item 1 click

                       supportFragmentManager.beginTransaction()
                           .add(R.id.fragmentContainer, HomeFragment.newInstance("", ""))

                           .commit()

                       true
                   }
                  R.id.checklist -> {
                      // Respond to navigation item 1 click
                      true
                  }
                  R.id.note-> {
                      // Respond to navigation item 1 click
                      true
                  }
                 R.id.diario -> {
                      // Respond to navigation item 1 click
                      true
                  }
                     R.id.calendario-> {
                        // Respond to navigation item 1 click

                         supportFragmentManager.beginTransaction()
                             .add(R.id.fragmentContainer, CalendarFragment.newInstance("", ""))
                             .addToBackStack(null)
                             .commit()

                        true
                    }
                 /* R.id.focus -> {
                      // Respond to navigation item 1 click
                      true
                  }
                  */

                  else -> {
                false
              }
            }
        }
*/

    }

    private fun setCurrentFragment(fragment: Fragment ) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
}


