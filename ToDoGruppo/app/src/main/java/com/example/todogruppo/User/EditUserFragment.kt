package com.example.todogruppo.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.todogruppo.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentEditUserBinding

    //dichiarazione variabili
    private lateinit var saveBtn: Button
    private lateinit var nome: EditText
    private lateinit var cognome: EditText
    private lateinit var dataNascita: EditText
    private lateinit var numeroTelefono: EditText
    private lateinit var email: EditText

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

        //inizializza variabili
        saveBtn = binding.saveBtn
        nome = binding.insNome
        cognome = binding.insCog
        dataNascita = binding.insData
        numeroTelefono = binding.insTel
        email = binding.insEmail

        //salva modifiche
        saveBtn.setOnClickListener {
            //torna indietro di un fragment
            parentFragmentManager.popBackStack()

            //serve per mostrare il pulsante "matita" tornando a  UserFragment
            UserFragment.istance?.showButton()
        }

    }


}