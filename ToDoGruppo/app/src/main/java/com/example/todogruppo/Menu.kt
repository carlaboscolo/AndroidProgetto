package com.example.todogruppo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todogruppo.calendar.CalendarFragment
import com.example.fragment.HomeFragment
import com.example.todogruppo.User.SettingsFragment
import com.example.todogruppo.User.UserFragment
import com.example.todogruppo.checklist.ListFragment
import com.example.todogruppo.databinding.HomeBinding
import com.example.todogruppo.diary.diaryFragment.DiaryFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationBarView

open class HomePage : MainActivity() {
    val name = "HOME"

    private lateinit var binding: HomeBinding

    //variabili
    private lateinit var titleUp: MaterialToolbar
    private lateinit var bottom_navigation: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.home)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fragment
        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val listFragment = ListFragment()
        val diaryFragment = DiaryFragment()
        val focusFragment = FocusFragment()
        val userProfile = UserFragment()
        val settings = SettingsFragment()

        //inizializza varibili
        titleUp = binding.mainToolbar
        bottom_navigation = binding.bottomNavigation

        //SETTARE LA HOME COME DEFAULT

        //serve per selezionare il pulsante home, appena apro il codice
        bottom_navigation.selectedItemId = R.id.home
        //serve per impostare come default il fragment della home
        setCurrentFragment(homeFragment)
        titleUp.title = "Home"


        //TOP BAR -> apre le impostazioni o il profilo utente
        titleUp.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    titleUp.title = "Impostazioni"
                    setCurrentFragment(settings)
                    true
                }
                R.id.profile -> {
                    titleUp.title = "Profilo"
                    setCurrentFragment(userProfile)
                    true
                }
                else -> false
            }
        }

        //BOTTOM BAR -> apre fragment task, diario, home, calendario, focus
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


    //funzione per selezionare il fragment da aprire
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }


}


