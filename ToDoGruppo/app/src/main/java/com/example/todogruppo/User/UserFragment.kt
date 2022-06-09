package com.example.todogruppo.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    //variabili
    private lateinit var editProfile : FloatingActionButton
    private lateinit var image : CardView

    //userFragment per gestire bottone 'matita'
    companion object {
        var istance: UserFragment? = null
    }

    init {
        istance = this
    }

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

        editProfile = binding.includeBtnEdit.editBtn
        image = binding.image

        editProfile.setOnClickListener{
            //quando viene cliccato, il bottone "matita" scompare perchè non si deve vedere in "editUserFragment"
            editProfile.visibility = View.GONE
            image.visibility =  View.GONE


        val editUserFragment  = EditUserFragment()

            //aprire il fragment per aggiornare i dati del profilo
            childFragmentManager.beginTransaction()
                .replace(R.id.userContainer, editUserFragment)
                .addToBackStack(null)
                .commit()
        }


    }

    //togliere il bottone dal fragment figlio
    fun showButton() {
        editProfile.visibility = View.VISIBLE
        image.visibility =  View.VISIBLE
    }


}