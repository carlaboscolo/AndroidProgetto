package com.example.todogruppo.checklist.note.noteFragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.todogruppo.checklist.note.viewModel.Note
import com.example.todogruppo.checklist.note.viewModel.NoteModel
import com.example.todogruppo.databinding.FragmentAddNoteBinding


class AddNoteFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddNoteBinding

    //view model
    val noteModel: NoteModel by viewModels()

    //note null -> nuovo task, altrimenti serve per la modifica
    private var note: Note? = null

    //varibili
    private lateinit var titletext : EditText
    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorSave: TextView
    private lateinit var errorSave2: TextView
    private lateinit var closeBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_note, container, false)
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se passo dei parametri la variabile note sarà valorizzata, altrimenti sarà nullo
        note = arguments?.getSerializable("note") as Note?

        //inizializza variabili
        addBtn = binding.newNoteButton

        titletext = binding.newNoteTitle
        inputText = binding.newNoteText
        loadingView = binding.loadingView
        errorSave = binding.errorSave
        errorSave2 = binding.errorSave2

        //salva la task inserita
        addBtn.setOnClickListener {

            loadingView.visibility = View.VISIBLE

            if (inputText.text.toString().isEmpty() && titletext.text.toString().isEmpty()) {
                errorSave.visibility = View.VISIBLE
                errorSave2.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")
            } else if (inputText.text.toString().isEmpty() ) {
                errorSave.visibility = View.GONE
                errorSave2.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")
            }else if (titletext.text.toString().isEmpty() ) {
                errorSave.visibility = View.VISIBLE
                errorSave2.visibility = View.GONE
                Log.d("error", "campo vuoto")
            } else {

                if (note == null) {
                    //salva la nuova task
                    noteModel.saveNote(titletext.text.toString(), inputText.text.toString())
                } else {
                    //modifica la task
                    noteModel.changeNote(
                        note!!._id,
                        titletext.text.toString(),
                        inputText.text.toString()
                    )
                }

                //torna indietro di un fragment
                parentFragmentManager.popBackStackImmediate()
            }

            //serve per mostrare il pulsante "+" tornando a Today Fragment
            NoteFragment.istance?.showButton()
        }


        //torna indietro senza salvare
        closeBtn = binding.closeBtn

        closeBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
            //serve per mostrare il pulsante "+" tornando a Today Fragment
            NoteFragment.istance?.showButton()
        }



        //settare le variabili per modificarle
        note?.let {
            titletext.setText(it._heading)
            inputText.setText(it._description)
        }

    }


}
