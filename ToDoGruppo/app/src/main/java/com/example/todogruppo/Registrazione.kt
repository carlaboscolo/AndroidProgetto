package com.example.todogruppo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.todogruppo.databinding.ActivityMainBinding
import com.example.todogruppo.databinding.ActivityRegistrazioneBinding

class Registrazione : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrazioneBinding

    //dichiarazione variabili
    private lateinit var username: EditText
    private lateinit var nome: EditText
    private lateinit var cognome: EditText
    private lateinit var dataNascita: EditText
    private lateinit var numeroTelefono: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var repeatPassword: EditText
    private lateinit var registratiBtn: Button
    private lateinit var accediBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_registrazione)
        binding = ActivityRegistrazioneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //inizializza variabili
        accediBtn = binding.accediBtn
        registratiBtn = binding.RegistratiButton
        username = binding.username
        nome = binding.nome
        cognome = binding.cognome
        dataNascita = binding.dataNascita
        numeroTelefono = binding.telefono
        email = binding.email
        password = binding.password
        repeatPassword = binding.repeatPassword


        registratiBtn.setOnClickListener {
            /* if (username.text.toString().isEmpty() && password.text.toString().isEmpty()){
                  Log.d("log", "credential empty ")
              }else { */
            launchActivity()
            /*  } */
        }

        accediBtn.setOnClickListener {
            launchAccediActivity()
        }

    }


    private fun launchActivity() {
        //val username = binding.username
        //val password = binding.password

        /* val us = "carlaboscolo@gmail.com"
         val psw = "123"

         if ((username.text.toString() == us) && (password.text.toString() == psw)) {

         */
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        /*
        } else {
            Log.d("log", "error credential")
        } */


    }

    private fun launchAccediActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}