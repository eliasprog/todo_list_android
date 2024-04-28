package com.elias.todolist.components

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.elias.todolist.R
import com.elias.todolist.model.ToDo
import com.elias.todolist.repository.ToDoRepository
import com.elias.todolist.ui.theme.Black
import com.elias.todolist.ui.theme.RBGreenSelected
import com.elias.todolist.ui.theme.RBRedSelected
import com.elias.todolist.ui.theme.RBYellowSelected
import com.elias.todolist.ui.theme.ShapeCardPriority
import com.elias.todolist.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ToDoItem(toDo: ToDo, context: Context, delete: (toDo: ToDo) -> Unit) {

    val scope = rememberCoroutineScope()
    val repository = ToDoRepository()

    fun dialogDelete() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog
            .setTitle("Deletar To Do")
            .setMessage("Deseja deleatar o To Do?")
            .setPositiveButton("Sim") { _, _ ->
                repository.deleteToDo(toDo.title.toString())
                scope.launch(Dispatchers.Main){
                    delete(toDo)
                }
            }
            .setNegativeButton("Não") { _, _  -> }
            .show()
    }

    var priorityText: String = when(toDo.priority) {
        0 -> { "Sem Prioridade" }
        1 -> { "Prioridade Baixa" }
        2 -> { "Prioridade Média" }
        else -> { "Prioriade Alta" }
    }

    var priorityColor: Color = when(toDo.priority) {
        0 -> { Black }
        1 -> { RBGreenSelected }
        2 -> { RBYellowSelected }
        else -> { RBRedSelected }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp)
        ) {
            val (
                txtTitle,
                txtDescription,
                cardPriority,
                txtPriority,
                btDelete
            ) = createRefs()

            Text(
                text = toDo.title.toString(),
                modifier = Modifier.constrainAs(txtTitle) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = toDo.description.toString(),
                modifier = Modifier.constrainAs(txtDescription) {
                    top.linkTo(txtTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = priorityText,
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = priorityColor
                ),
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(cardPriority) {
                        top.linkTo(txtDescription.bottom, margin = 10.dp)
                        start.linkTo(txtPriority.end, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                shape = ShapeCardPriority.large
            ) {}

            IconButton(
                onClick = { dialogDelete() },
                modifier = Modifier.constrainAs(btDelete) {
                    top.linkTo(txtDescription.bottom, margin = 10.dp)
                    start.linkTo(cardPriority.end, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            ) {
               Image(
                   imageVector = ImageVector.vectorResource(R.drawable.id_delete),
                   contentDescription = null
               )
            }
        }
    }
}
