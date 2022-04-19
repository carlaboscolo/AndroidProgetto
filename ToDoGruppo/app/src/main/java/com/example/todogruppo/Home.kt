package com.example.todogruppo

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomePage : MainActivity() {
    val name = "HOME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

/*
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, HomeFragment.newInstance("", ""))
        transaction.commit()
*/

        NavigationBarView.OnItemSelectedListener { item ->
              when (item.itemId) {
                   R.id.home -> {
                    // Respond to navigation item 1 click
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
    }

}


