package com.elias.todolist.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elias.todolist.components.TDButton
import com.elias.todolist.components.TDTextField
import com.elias.todolist.constants.Constants
import com.elias.todolist.repository.ToDoRepository
import com.elias.todolist.ui.theme.Purple700
import com.elias.todolist.ui.theme.RBGreenDisable
import com.elias.todolist.ui.theme.RBGreenSelected
import com.elias.todolist.ui.theme.RBRedDisable
import com.elias.todolist.ui.theme.RBRedSelected
import com.elias.todolist.ui.theme.RBYellowDisable
import com.elias.todolist.ui.theme.RBYellowSelected
import com.elias.todolist.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveToDo(navController: NavController) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repository = ToDoRepository()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Salvar To Do",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple700
                ),
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = White
                            )
                        }
                    } else { null }
                }
            )
        }
    ) { innerPaddingValues ->

        var title by remember {
            mutableStateOf("")
        }

        var description by remember {
            mutableStateOf("")
        }

        var lowPriority by remember {
            mutableStateOf(false)
        }

        var mediumPriority by remember {
            mutableStateOf(false)
        }

        var hightPriority by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPaddingValues)
        ) {
            TDTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Título",
                maxlines = 1,
                keyboardType = KeyboardType.Text
            )

            TDTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxlines = 5,
                keyboardType = KeyboardType.Text
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Prioridade: ")

                RadioButton(
                    selected = lowPriority,
                    onClick = {
                        lowPriority = !lowPriority
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RBGreenSelected,
                        unselectedColor = RBGreenDisable
                    )
                )

                RadioButton(
                    selected = mediumPriority,
                    onClick = {
                        mediumPriority = !mediumPriority
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RBYellowSelected,
                        unselectedColor = RBYellowDisable
                    )
                )

                RadioButton(
                    selected = hightPriority,
                    onClick = {
                        hightPriority = !hightPriority
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = RBRedSelected,
                        unselectedColor = RBRedDisable
                    )
                )
            }

            TDButton(
                onClick = {
                    var message: Boolean = true

                    scope.launch(Dispatchers.IO) {
                        if (title.isEmpty() || description.isEmpty()) {
                            message = false
                        } else {
                            val priority: Int = if (lowPriority)
                                Constants.LOW_PRIORITY
                            else if (mediumPriority)
                                Constants.MEDIUM_PRIORITY
                            else if (hightPriority)
                                Constants.HIGH_PRIORITY
                            else
                                Constants.NO_PRIORITY

                            repository.saveToDo(title, description, priority)
                            message = true
                        }
                    }

                    scope.launch(Dispatchers.Main){
                        if (message) {
                            title = String()
                            description = String()
                            lowPriority = false
                            mediumPriority = false
                            hightPriority = false

                            navController.popBackStack()

                            Toast.makeText(context, "Sucesso ao salvar to do", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Título e Descrição são obrigatórios", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = "Salvar"
            )
        }
    }
}