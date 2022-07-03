package com.example.todogruppo

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.todogruppo.databinding.ActivityMainBinding
import com.example.todogruppo.databinding.FragmentListBinding


open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //dichiarazione variabili
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var registrazione: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inizializza variabili
        username = binding.username
        password = binding.password
        login   = binding.button
        registrazione = binding.RegistratiBtn


        login.setOnClickListener {
            /*
              if (username.text.toString().isEmpty() && password.text.toString().isEmpty()){
                  Log.d("log", "credential empty ")
              }else { */
            launchSecondActivity()
            /*  } */
        }

        registrazione.setOnClickListener{
            launchRegisterActivity()
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


    private fun launchRegisterActivity() {
        val intent = Intent(this, Registrazione::class.java)
        startActivity(intent)
    }



}