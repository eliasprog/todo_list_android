package com.elias.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elias.todolist.constants.Constants
import com.elias.todolist.ui.theme.ToDoListTheme
import com.elias.todolist.view.SaveToDo
import com.elias.todolist.view.ToDoList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Constants.TODOVIEW) {
                    composable(route = Constants.TODOVIEW) {
                        ToDoList(navController)
                    }

                    composable(route = Constants.SAVETODOVIEW) {
                        SaveToDo(navController)
                    }
                }
            }
        }
    }
}