package com.example.todogruppo.diary.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class DiaryModel : ViewModel(){

    var diaryList = MutableLiveData<ArrayList<Diary>>()

    //funzione salva note
    fun saveDiary(title: String, textDiary : String ="testo di prova", data : String /*,  imageId  : String */){

        //FIREBASE
        val db = Firebase.firestore
        // crea una nuova  pagina di diario con titolo, testo e data
        val diary  = hashMapOf(
            "title" to title,
            "textDiary" to textDiary,
            "data" to data,
           // "imageId" to imageId
        )

        // aggiungi un nuovo documenti
        db.collection("diary")
            .add(diary)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }

    //funzione che carica i dati nell'applicazione
    fun getDiary(/*date : String*/){
        val db = Firebase.firestore

        db.collection("diary")
            .get()
            .addOnSuccessListener { result ->

                var diaryArray = ArrayList<Diary>()

                for (document in result) {
                    val diary = Diary(
                        document.data.getValue("title").toString(),
                        document.data.getValue("textDiary").toString(),
                        document.data.getValue("data").toString(),
                        document.id
                        //, document.data.getValue("imageId").toString()
                    )

                    //controllo che la data di oggi non sia già stata inserita
                  /*  if(diary.data == date){
                        Log.d("error", "La data di oggi è già stata inserita")
                    }else if(date > calendar()){
                        Log.d("error", "Non puoi inserire una data del futuro")
                    }else{
                        Log.d("success", "Data accettata")
                    } */

                    diaryArray.add(diary)
                }
                diaryList.value = diaryArray

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }
    }


    //cancella un dato con uno swipe
    fun deleteDiary(id:String){

        val db = Firebase.firestore

        db.collection("diary").document(id)
            .delete()
            .addOnSuccessListener { Log.d( "success","DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w( "Error deleting document", e) }

    }

    //funzione per cambiare i dati
    fun changeDiary(id: String, title: String, textDiary : String ="testo di prova", data : String,/* imageId  : String */){

        val db = Firebase.firestore

        // aggiorna una task con nome e data
        db.collection("diary").document(id)
            .update(mapOf(
                "title" to title,
                "textDiary" to textDiary,
                "data" to data,
                //"imageId" to imageId
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



    //funzione che carica i dati nell'applicazione in base ad una data
    fun  getDateDiary(date : String){
        val db = Firebase.firestore

        db.collection("diary")
            .get()
            .addOnSuccessListener { result ->

                var diaryArray = ArrayList<Diary>()

                for (document in result) {
                    val diary = Diary(
                        document.data.getValue("title").toString(),
                        document.data.getValue("textDiary").toString(),
                        document.data.getValue("data").toString(),
                        document.id
                        //, document.data.getValue("imageId").toString()
                    )


                    //suddivisione delle date
                    if(diary.data == date) {
                        diaryArray.add(diary)
                        Log.d("okey", diaryArray.toString())
                    }else{
                        Log.d("error", "le date non sono uguali")
                    }

                }
                diaryList.value = diaryArray

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreExample", "Error getting documents.", exception)
            }
    }

}


