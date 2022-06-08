package com.example.todogruppo.checklist.task.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todogruppo.checklist.task.taskFragment.TodayFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ViewModel: ViewModel() {

    var taskList = MutableLiveData<ArrayList<Task>>()

    //funzione salva task
     fun saveTask(nomeTask : String, data: String){
        //FIREBASE
        val db = Firebase.firestore
        // crea una nuova task con nome e data
        val task  = hashMapOf(
            "name" to nomeTask,
            "data" to data
        )

        // aggiungi un nuovo documenti
        db.collection("task")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }

    //funzione che carica i dati nell'applicazione
     fun getTask(type: Int){
        val db = Firebase.firestore

        db.collection("task")
            .get()
            .addOnSuccessListener { result ->

                var taskArray = ArrayList<Task>()

                for (document in result) {
                    val task = Task(
                        document.data.getValue("name").toString(),
                        document.data.getValue("data").toString(),
                        document.id
                    )

                    //suddivisione delle date
                    if(type == TodayFragment.TYPE_NO_DEADLINE && task.data == "Nessuna Scadenza"){   //Nessuna scadenza
                        taskArray.add(task)
                    }else if(type == TodayFragment.TYPE_TODAY && task.data == calendar()){           //data di oggi
                        taskArray.add(task)
                    }else if(task.data != "Nessuna Scadenza" && task.data != calendar()) {           //con una scadenza                                                                //con una scadenza
                        taskArray.add(task)
                       /*
                        if(task.data != "Nessuna Scadenza" && task.data != calendar()){
                            taskArray.add(task)
                        }
                       */
                    }

                }
                taskList.value = taskArray


            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }
    }


    //cancella un dato con uno swipe
    fun delete(id:String){

        val db = Firebase.firestore

        db.collection("task").document(id)
            .delete()
            .addOnSuccessListener { Log.d( "success","DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w( "Error deleting document", e) }

    }

  //funzione per cambiare i dati
    fun change(id: String, nomeTask : String, data: String){

      val db = Firebase.firestore

      // aggiorna una task con nome e data
      db.collection("task").document(id)
          .update(mapOf(
              "name" to nomeTask,
              "data" to data
          ))
          .addOnSuccessListener { Log.d( "success","DocumentSnapshot successfully changed!") }
          .addOnFailureListener { e -> Log.w( "Error not change document", e) }

    }

    //selezionare la data di oggi
    fun calendar(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH]+1

        val dayString = if(day<10) "0$day" else "$day"
        val monthString = if(month<10) "0$month" else "$month"

        val data_string = "$dayString-$monthString-$year"

        Log.d("data", data_string)
        return data_string
    }



}

