package com.example.todogruppo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.todogruppo.databinding.ActivityMainBinding
import com.example.todogruppo.databinding.FragmentListBinding


open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //variabili
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val button = binding.button

        button.setOnClickListener {
            /*
              if (username.text.toString().isEmpty() && password.text.toString().isEmpty()){
                  Log.d("log", "credential empty ")
              }else { */
            launchSecondActivity()
            /*  } */
        }
    }


    private fun launchSecondActivity() {
        val username = binding.username
        val password = binding.password

        /* val us = "carlaboscolo@gmail.com"
         val psw = "123"

         if ((username.text.toString() == us) && (password.text.toString() == psw)) {

         */
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

        /*
        } else {
            Log.d("log", "error credential")
        } */
    }


}