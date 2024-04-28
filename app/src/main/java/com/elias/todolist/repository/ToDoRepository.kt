package com.elias.todolist.repository

import com.elias.todolist.datasource.DataSource
import com.elias.todolist.model.ToDo
import kotlinx.coroutines.flow.Flow

class ToDoRepository() {

    private val dataSource = DataSource()

    fun saveToDo(title: String, description: String, priority: Int) {
        dataSource.saveToDo(title, description, priority)
    }

    fun fetchToDos(): Flow<MutableList<ToDo>> {
        return dataSource.fetchToDos()
    }

    fun deleteToDo(title: String) {
        dataSource.deleteToDo(title)
    }
}