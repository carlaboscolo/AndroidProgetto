package com.example.todogruppo.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fragment.HomeFragment
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment() {

    private lateinit var binding: FragmentEditUserBinding

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
        binding = FragmentEditUserBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        saveBtn = binding.saveBtn

        saveBtn.setOnClickListener {
            //torna indietro di un fragment
            parentFragmentManager.popBackStack()

            //serve per mostrare il pulsante "matita" tornando a  UserFragment
            UserFragment.istance?.showButton()
        }

    }


}