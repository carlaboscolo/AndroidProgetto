package com.example.todogruppo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewModel: ViewModel() {

    //funzione salva task
     fun saveTask(nomeTask : String, data: String){
        //FIREBASE
        val db = Firebase.firestore
        // Create a new user with a first and last name
        val task  = hashMapOf(
            "name" to nomeTask,
            "data" to data
        )

        // Add a new document with a generated ID
        db.collection("task")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}