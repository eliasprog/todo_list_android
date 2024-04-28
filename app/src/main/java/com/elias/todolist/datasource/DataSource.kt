package com.elias.todolist.datasource

import com.elias.todolist.constants.Constants
import com.elias.todolist.model.ToDo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DataSource {

    private val db = FirebaseFirestore.getInstance()

    private val _toDos = MutableStateFlow<MutableList<ToDo>>(mutableListOf())
    private val toDos: StateFlow<MutableList<ToDo>> = _toDos

    fun saveToDo(title: String, description: String, priority: Int) {
        val toDoMap = hashMapOf(
            "title" to title,
            "description" to description,
            "priority" to priority
        )

        db.collection(Constants.COLLECTION)
            .document(title)
            .set(toDoMap).addOnCompleteListener {

            }.addOnFailureListener {

            }
    }

    fun fetchToDos(): Flow<MutableList<ToDo>> {
        val toDosTemp: MutableList<ToDo> = mutableListOf()

        db.collection(Constants.COLLECTION)
            .get()
            .addOnCompleteListener { querySnapshot ->
            if (querySnapshot.isSuccessful) {
                for (document in querySnapshot.result) {
                    val toDo = document.toObject<ToDo>()
                    toDosTemp.add(toDo)
                }
                _toDos.value = toDosTemp
            }
        }

        return toDos
    }

    fun deleteToDo(title: String) {
        db.collection(Constants.COLLECTION).document(title).delete().addOnCompleteListener {

        }.addOnFailureListener {

        }
    }
}