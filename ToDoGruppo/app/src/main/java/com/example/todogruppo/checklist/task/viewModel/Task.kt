package com.example.todogruppo.checklist.task.viewModel

import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListAdapter
import java.io.Serializable

//Serializable passa dati di tipo complesso (es. classi)
data class Task(val _heading: String, val _data: String, val _id: String, val _check:  Boolean = false) : Serializable {
    var heading = _heading
    var data = _data
    var id = _id
    var check = _check
}