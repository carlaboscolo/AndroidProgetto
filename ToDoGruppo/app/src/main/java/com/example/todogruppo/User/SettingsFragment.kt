package com.example.todogruppo.User

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.todogruppo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSettingsBinding

    //dichiarazione variabili
    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inizializza variabili
        logout = binding.logout

        logout.setOnClickListener {
          Log.d("okey", "logout dall'app. Arrivederci")
        }

    }
}