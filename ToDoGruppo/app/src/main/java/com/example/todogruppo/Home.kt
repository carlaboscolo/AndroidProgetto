package com.example.todogruppo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class Home : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

    val transaction = supportFragmentManager.beginTransaction()
        .add(R.id.fragmentContainer, HomeFragment.newInstance("", ""))
    transaction.commit()

    NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                // Respond to navigation item 1 click
                true
            }
            else -> {
                false
            }
        }

    }
}


}