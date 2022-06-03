package com.example.todogruppo.checklist.note.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class NoteModel: ViewModel() {

    var noteList = MutableLiveData<ArrayList<Note>>()

    //funzione salva note
    fun saveNote(nomeNote : String){
        //FIREBASE
        val db = Firebase.firestore
        // crea una nuova task con nome e data
        val note  = hashMapOf(
            "name" to nomeNote
        )

        // aggiungi un nuovo documenti
        db.collection("note")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }

    //funzione che carica i dati nell'applicazione
    fun getNote(){
        val db = Firebase.firestore

        db.collection("note")
            .get()
            .addOnSuccessListener { result ->

                var noteArray = ArrayList<Note>()

                for (document in result) {
                    val note = Note(
                        document.data.getValue("name").toString(),
                        document.id
                    )

                    noteArray.add(note)
                }
                noteList.value = noteArray

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }
    }


    //cancella un dato con uno swipe
    fun deleteNote(id:String){

        val db = Firebase.firestore

        db.collection("note").document(id)
            .delete()
            .addOnSuccessListener { Log.d( "success","DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w( "Error deleting document", e) }

    }

    //funzione per cambiare i dati
    fun changeNote(id: String, nomeTask : String){

        val db = Firebase.firestore

        // aggiorna una task con nome e data
        db.collection("note").document(id)
            .update(mapOf(
                "name" to nomeTask
            ))
            .addOnSuccessListener { Log.d( "success","DocumentSnapshot successfully changed!") }
            .addOnFailureListener { e -> Log.w( "Error not change document", e) }

    }



}

