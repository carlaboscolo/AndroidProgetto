package com.example.lista

import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListAdapter

data class Task(val _heading: String, val _data: String){
    var heading = _heading
    var data = _data
}