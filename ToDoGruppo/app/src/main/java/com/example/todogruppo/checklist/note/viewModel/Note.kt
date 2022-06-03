package com.example.todogruppo.checklist.note.viewModel

import java.io.Serializable

//Serializable passa dati di tipo complesso (es. classi)
data class Note(val _heading: String, val _id: String) : Serializable {
    var heading = _heading
    var id = _id
}