package com.example.todogruppo.checklist.note.noteFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.todogruppo.checklist.note.viewModel.Note
import com.example.todogruppo.checklist.note.viewModel.NoteModel
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.example.todogruppo.databinding.FragmentAddNoteBinding
import java.util.*

class AddNoteFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddNoteBinding

    //view model
    val noteModel: NoteModel by viewModels()

    //task null -> nuovo task, altrimenti serve per la modifica
    private var note: Note? = null

    //varibili
    private lateinit var inputText: EditText
    private lateinit var addBtn: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorSave: TextView
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

        //se passo dei parametri la variabile task sarà valorizzata, altrimenti sarà nullo
        note = arguments?.getSerializable("note") as Note?

        //inizializza variabili
        addBtn = binding.newNoteButton
        inputText = binding.newNoteText
        loadingView = binding.loadingView
        errorSave = binding.errorSave

        //salva la task inserita
        addBtn.setOnClickListener {

            loadingView.visibility = View.VISIBLE

            if (inputText.text.toString().isEmpty()) {
                errorSave.visibility = View.VISIBLE
                Log.d("error", "campo vuoto")
            } else {

                if (note == null) {
                    //salva la nuova task
                    noteModel.saveNote(inputText.text.toString())
                } else {
                    //modifica la task
                    noteModel.changeNote(
                        note!!._id,
                        inputText.text.toString()
                    )
                }

                //torna indietro di un fragment
                parentFragmentManager.popBackStackImmediate()
            }

            //serve per mostrare il pulsante "+" tornando a Today Fragment
            TodayFragment.istance?.showButton()
        }


        //torna indietro senza salvare
        closeBtn = binding.closeBtn

        closeBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
            //serve per mostrare il pulsante "+" tornando a Today Fragment
            TodayFragment.istance?.showButton()
        }



        //settare le variabili per modificarle
        note?.let {
            inputText.setText(it._heading)
        }

    }


}
