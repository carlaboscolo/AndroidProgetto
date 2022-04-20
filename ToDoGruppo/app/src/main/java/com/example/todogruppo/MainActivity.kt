package com.example.todogruppo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText


open class MainActivity : AppCompatActivity() {


    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.button)

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
    val username = findViewById<EditText>(R.id.username)
    val password = findViewById<EditText>(R.id.password)

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