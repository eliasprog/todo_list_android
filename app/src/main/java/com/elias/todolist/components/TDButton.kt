package com.elias.todolist.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.elias.todolist.ui.theme.LightBlue
import com.elias.todolist.ui.theme.ShapeTextField
import com.elias.todolist.ui.theme.White

@Composable
fun TDButton(onClick: () -> Unit,
             modifier: Modifier,
             text: String) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightBlue,
            contentColor = White
        ),
        shape = ShapeTextField.small
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}