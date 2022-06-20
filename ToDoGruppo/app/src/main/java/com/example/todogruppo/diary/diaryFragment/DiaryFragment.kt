package com.example.todogruppo.diary.diaryFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

open class DiaryFragment : Fragment() {

    //view model
    val diaryModel: DiaryModel by viewModels()

    //variabili
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var DiaryRecyclerView: RecyclerView
    private lateinit var aggiungiDiario : FloatingActionButton

    //DiaryFragment per gestire '+'
    companion object {
        var istance: DiaryFragment? = null
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
        //return inflater.inflate(R.layout.fragment_diary, container, false)
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bottone che serve ad aggiungere una pagina di diario
        aggiungiDiario = binding.includeBtnDiary.addNewBtn

        aggiungiDiario.setOnClickListener {
            //quando viene cliccato, il bottone "+" scompare perchè non si deve vedere in "addNote"
            aggiungiDiario.visibility = View.GONE

            val diary = AddDiaryFragment()

            //aprire il fragment per la nuova pagina di diario
            childFragmentManager.beginTransaction()
                .replace(R.id.newDiaryContainer, diary)
                .addToBackStack(null)
                .commit()
        }

        //diary model -> ottieni i dati
        diaryModel.getDiary()

        //esegui operazioni sulla lista delle task
        diaryModel.diaryList.observe(viewLifecycleOwner) {
            DiaryRecyclerView = binding.diaryRecyclerView
            DiaryRecyclerView.setHasFixedSize(true)

            val adapterDiary = DiaryAdapter(it, diaryModel, view.getContext())

            adapterDiary.setOnCallback(object : DiaryAdapter.AdapterCallback {

                //cliccare per modificare
                override fun selectItem(position: Int) {
                    //nasconde il pulsante "+" quando apro "addDiary"
                    aggiungiDiario.visibility = View.GONE

                    val diary = AddDiaryFragment()
                    val bundle = Bundle()

                    //bundle serve per passare i parametri nei fragment
                    bundle.putSerializable("diary", it[position])

                    diary.arguments = bundle

                    //aprire il fragment new diary
                    childFragmentManager.beginTransaction()
                        .replace(R.id.newDiaryContainer, diary)
                        .addToBackStack(null)
                        .commit()
                }

            })


            //carica la lista delle pagine di diario
            DiaryRecyclerView.apply {
                DiaryRecyclerView.adapter = adapterDiary

                DiaryRecyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            }

            //eliminare una giornata di diario
            val callback: ItemTouchHelper.Callback = DiarySwipeHelperCallback(adapterDiary)
            var mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper?.attachToRecyclerView(DiaryRecyclerView)
        }
    }

    //togliere il bottone dal fragment figlio
    fun showButton() {
        aggiungiDiario.visibility = View.VISIBLE
    }

}

