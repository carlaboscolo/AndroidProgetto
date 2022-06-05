package com.example.todogruppo.diary.diaryFragment

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
import com.example.todogruppo.checklist.task.deleteTask.DiarySwipeHelperCallback
import com.example.todogruppo.databinding.FragmentDiaryBinding
import com.example.todogruppo.diary.viewModel.DiaryAdapter
import com.example.todogruppo.diary.viewModel.DiaryModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DiaryFragment : Fragment() {

    //view model
    val diaryModel: DiaryModel by viewModels()

    //variabili
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var DiaryRecyclerView: RecyclerView
    private lateinit var aggiungiDiario : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_diary, container, false)
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bottone che serve ad aggiungere una pagina di diario
        aggiungiDiario = binding.includeBtn.addNewBtn

        aggiungiDiario.setOnClickListener {
            //quando viene cliccato, il bottone "+" scompare perchè non si deve vedere in "addNote"
            aggiungiDiario.visibility = View.GONE

            val note = AddDiaryFragment()

            //aprire il fragment per la nuova task
            childFragmentManager.beginTransaction()
                .replace(R.id.newDiaryContainer, note)
                .addToBackStack(null)
                .commit()
        }

        //diary model -> ottieni i dati
        diaryModel.getDiary()

        //esegui operazioni sulla lista delle task
        diaryModel.diaryList.observe(viewLifecycleOwner) {
            DiaryRecyclerView = binding.diaryRecyclerView
            DiaryRecyclerView.setHasFixedSize(true)

            val adapterNote = DiaryAdapter(it, diaryModel, view.getContext())

            adapterNote.setOnCallback(object : DiaryAdapter.AdapterCallback {

                //cliccare per modificare
                override fun selectItem(position: Int) {
                    //nasconde il pulsante "+" quando apro "addNote"
                    aggiungiDiario.visibility = View.GONE

                    val note = AddDiaryFragment()
                    val bundle = Bundle()

                    //bundle serve per passare i parametri nei fragment
                    bundle.putSerializable("note", it[position])

                    note.arguments = bundle

                    //aprire il fragment new task
                    childFragmentManager.beginTransaction()
                        .replace(R.id.newDiaryContainer, note)
                        .addToBackStack(null)
                        .commit()
                }

            })


            //carica la lista delle pagine di diario
            DiaryRecyclerView.apply {
                DiaryRecyclerView.adapter = adapterNote

                DiaryRecyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            }

            //eliminare una nota
            val callback: ItemTouchHelper.Callback = DiarySwipeHelperCallback(adapterNote)
            var mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper?.attachToRecyclerView(DiaryRecyclerView)
        }
    }

    //togliere il bottone dal fragment figlio
    fun showButton() {
        aggiungiDiario.visibility = View.VISIBLE
    }

}
