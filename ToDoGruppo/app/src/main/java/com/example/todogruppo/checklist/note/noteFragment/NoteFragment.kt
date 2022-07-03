package com.example.todogruppo.checklist.note.noteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todogruppo.R
import com.example.todogruppo.checklist.note.viewModel.NoteAdapter
import com.example.todogruppo.checklist.note.viewModel.NoteModel
import com.example.todogruppo.checklist.task.deleteTask.NoteItemTouchHelper
import com.example.todogruppo.checklist.task.deleteTask.NoteSwipeHelperCallback
import com.example.todogruppo.checklist.task.deleteTask.SwipeHelperCallback
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.example.todogruppo.databinding.FragmentNoteBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class NoteFragment : Fragment() {

    //view model
    val noteModel: NoteModel by viewModels()

    //variabili
    private lateinit var binding: FragmentNoteBinding
    private lateinit var NoteRecyclerView: RecyclerView
    private lateinit var aggiungiNote: FloatingActionButton

    //NoteFragment per gestire '+'
    companion object {
        var istance: NoteFragment? = null
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
        //  return inflater.inflate(R.layout.fragment_note, container, false)
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //bottone che serve ad aggiungere una nota
        aggiungiNote = binding.includeBtn.addNewBtn

        aggiungiNote.setOnClickListener {
            //quando viene cliccato, il bottone "+" scompare perchè non si deve vedere in "addNote"
            aggiungiNote.visibility = View.GONE

            val note = AddNoteFragment()

            //aprire il fragment per la nuova nota
            childFragmentManager.beginTransaction()
                .replace(R.id.newNoteContainer, note)
                .addToBackStack(null)
                .commit()
        }

        //note model -> ottieni i dati
        noteModel.getNote()

        //esegui operazioni sulla lista delle note
        noteModel.noteList.observe(viewLifecycleOwner) {
            NoteRecyclerView = binding.noteRecyclerView
            NoteRecyclerView.setHasFixedSize(true)

            val adapterNote = NoteAdapter(it, noteModel, view.getContext())

            adapterNote.setOnCallback(object : NoteAdapter.AdapterCallback {

                //cliccare per modificare
                override fun selectItem(position: Int) {
                    //nasconde il pulsante "+" quando apro "addNote"
                    aggiungiNote.visibility = View.GONE

                    val note = AddNoteFragment()
                    val bundle = Bundle()

                    //bundle serve per passare i parametri nei fragment
                    bundle.putSerializable("note", it[position])

                    note.arguments = bundle

                    //aprire il fragment new note
                    childFragmentManager.beginTransaction()
                        .replace(R.id.newNoteContainer, note)
                        .addToBackStack(null)
                        .commit()
                }

            })

            //carica la lista delle note
            NoteRecyclerView.apply {
                NoteRecyclerView.adapter = adapterNote

                NoteRecyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            }

            //eliminare una nota
            val callback: ItemTouchHelper.Callback = NoteSwipeHelperCallback(adapterNote)
            var mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper?.attachToRecyclerView(NoteRecyclerView)
        }
    }

    //togliere il bottone dal fragment figlio
    fun showButton() {
        aggiungiNote.visibility = View.VISIBLE
    }

}
