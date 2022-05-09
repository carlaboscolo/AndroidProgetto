package com.example.todogruppo.TaskList

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ado.MyAdapter
import com.example.lista.Task
import com.example.todogruppo.R
import com.example.todogruppo.databinding.FragmentAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddTaskFragment : Fragment() {

    //lateinit -> inizializza variabile più tardi
    private lateinit var binding: FragmentAddTaskBinding

    private lateinit var CalendarBtn: Button
    private lateinit var selectedDate: TextView

    private lateinit var inputText: EditText
    private lateinit var addBtn: Button

    private lateinit var TaskList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_task, container, false)
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    /* FIREBASE BASE
        val db = Firebase.firestore
        // Create a new user with a first and last name
        val task  = hashMapOf(
            "name" to "Task 1",
        )

// Add a new document with a generated ID
        db.collection("task")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
*/

        addBtn = view.findViewById(R.id.newTaskButton)
        inputText = view.findViewById(R.id.newTaskText)

        addBtn.setOnClickListener {
            //saveTaskDb()

            //torna indietro di un fragment
            parentFragmentManager.popBackStackImmediate()
        }

        CalendarBtn = view.findViewById(R.id.addDataBtn)
        selectedDate = view.findViewById(R.id.selected_data)

        //MaterialDatePicker
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        CalendarBtn.setOnClickListener {
            //far comparire il calendario
            datePicker.show(parentFragmentManager, "tag");

            datePicker.addOnPositiveButtonClickListener {
                // Respond to positive button click.
                selectedDate.setText(datePicker.headerText)
            }

        }

    }

/*
    private fun saveTaskDb() {
        val db = Firebase.firestore

        db.collection("task")
            .get()
            .addOnSuccessListener { result ->


                var userArray = ArrayList<Task>()

                for (document in result) {
                    val tasks = Task(
                        document.data.getValue("heading").toString()
                       // document.data.getValue("date") as DatePicker
                    )
                    userArray.add(tasks)
                }

                TaskList.apply {
                    adapter = MyAdapter(userArray)
                   // layoutManager = LinearLayoutManager(LinearLayoutManager.VERTICAL, false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }

    }
*/
}


