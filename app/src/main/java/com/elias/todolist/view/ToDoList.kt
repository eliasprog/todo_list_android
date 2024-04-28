package com.elias.todolist.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elias.todolist.R
import com.elias.todolist.components.ToDoItem
import com.elias.todolist.constants.Constants
import com.elias.todolist.repository.ToDoRepository
import com.elias.todolist.ui.theme.Black
import com.elias.todolist.ui.theme.Purple700
import com.elias.todolist.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoList(navController: NavController) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val repository = ToDoRepository()

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
           LargeTopAppBar(
               title = {
                    Text(
                        text = "To Do List",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
               },
               colors = TopAppBarDefaults.topAppBarColors(
                   containerColor = Purple700
               ),
               scrollBehavior = scrollBehavior
           )
        },
        containerColor = Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("saveToDo")
                },
                containerColor = Purple700
            ) {
               Image(
                   imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                   contentDescription = null
               )
            }
        }
    ) { innerPadding ->

        var toDoList = repository
            .fetchToDos()
            .collectAsState(mutableListOf())
            .value

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            content = {
                itemsIndexed(toDoList) { _, item ->
                    ToDoItem(item, context) { toDo ->
                        toDoList.remove(toDo)
                        navController.navigate(Constants.TODOVIEW)
                        Toast.makeText(
                            context,
                            "Sucesso ao deletar To Do!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }
}
