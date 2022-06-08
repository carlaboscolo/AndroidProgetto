package com.example.todogruppo.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fragment.HomeFragment
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentSettingsBinding
import com.example.todogruppo.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding : FragmentUserBinding

    //variabili
    private lateinit var saveBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user, container, false)
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        saveBtn = binding.saveBtn

        saveBtn.setOnClickListener {
            //aggiungere profilo base
        }

    }


}