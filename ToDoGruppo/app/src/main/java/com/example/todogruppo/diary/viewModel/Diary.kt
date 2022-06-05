package com.example.todogruppo.diary.viewModel

import java.io.Serializable

//Serializable passa dati di tipo complesso (es. classi)
data class Diary(val _heading: String, val _description: String, val _data: String, val _id: String) : Serializable {
    var heading = _heading
    var description = _description
    var data = _data
    var id = _id
}
