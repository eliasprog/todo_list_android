package com.elias.todolist.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.elias.todolist.ui.theme.Black
import com.elias.todolist.ui.theme.LightBlue
import com.elias.todolist.ui.theme.ShapeTextField
import com.elias.todolist.ui.theme.White

@Composable
fun TDTextField(value: String,
              onValueChange: (String) -> Unit,
              modifier: Modifier,
              label: String,
              maxlines: Int,
              keyboardType: KeyboardType) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(text = label)
        },
        maxLines = maxlines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Black,
            focusedBorderColor = LightBlue,
            focusedLabelColor = LightBlue,
            focusedContainerColor = White,
            cursorColor = White
        ),
        shape = ShapeTextField.small,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}
