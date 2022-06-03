package com.example.todogruppo.checklist.note.viewModel

class NoteModel : androidx.lifecycle.ViewModel() {

      /*  var taskList = MutableLiveData<ArrayList<Task>>()

        //funzione salva task
        fun saveTask(nomeTask: String, data: String) {
            //FIREBASE
            val db = Firebase.firestore
            // crea una nuova task con nome e data
            val task = hashMapOf(
                "name" to nomeTask,
                "data" to data
            )

            // aggiungi un nuovo documenti
            db.collection("task")
                .add(task)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }

        }

        //funzione che carica i dati nell'applicazione
        fun getTask(type: Int) {
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

                        if (type == TodayFragment.TYPE_NO_DEADLINE && task.data == "Nessuna Scadenza") {
                            taskArray.add(task)
                        } else if (type == TodayFragment.TYPE_TODAY && task.data == calendar()) {
                            taskArray.add(task)
                        } else {
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
        fun delete(id: String) {

            val db = Firebase.firestore

            db.collection("task").document(id)
                .delete()
                .addOnSuccessListener { Log.d("success", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("Error deleting document", e) }

        }

        //funzione per cambiare i dati
        fun change(id: String, nomeTask: String, data: String) {

            val db = Firebase.firestore

            // aggiorna una task con nome e data
            db.collection("task").document(id)
                .update(
                    mapOf(
                        "name" to nomeTask,
                        "data" to data
                    )
                )
                .addOnSuccessListener { Log.d("success", "DocumentSnapshot successfully changed!") }
                .addOnFailureListener { e -> Log.w("Error not change document", e) }

        }

      */

    }

